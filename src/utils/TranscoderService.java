package utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.SystemPropertiesCredentialsProvider;
import com.amazonaws.services.elastictranscoder.AmazonElasticTranscoder;
import com.amazonaws.services.elastictranscoder.AmazonElasticTranscoderClient;
import com.amazonaws.services.elastictranscoder.model.CreateJobOutput;
import com.amazonaws.services.elastictranscoder.model.CreateJobRequest;
import com.amazonaws.services.elastictranscoder.model.CreateJobResult;
import com.amazonaws.services.elastictranscoder.model.JobInput;
import com.amazonaws.services.sns.AmazonSNSClient;
import com.amazonaws.services.simpleemail.*;
import com.amazonaws.services.simpleemail.model.*;
import com.amazonaws.regions.*;

public class TranscoderService {
	static AmazonElasticTranscoder transCoder;
	public static String myTopicArn ;
	static AmazonSNSClient sns;
	public static String PIPELINEID = "1431036086821-3x8gy0";
	//public static String PIPELINEID;
	public static String PRESETID = "1351620000001-100190";
	
	static final String FROM = "yashi.kamboj@gmail.com";
//	static final String TO = "manoj.gyanani@sjsu.edu";
//	static final String BODY = "Your request conversion from" + ori + "to" + res + "is completed.";
//	static final String SUBJECT = "File is ready for download";
	
	
	public static int transcodeProcess(String emailid, String ori, String res) throws Exception{
		transCoder = init();
		//sns = snsinit();
		//create SNS request
		//myTopicArn = sns.createTopic(new CreateTopicRequest("TranscodeTopic")).getTopicArn();
		//sns.subscribe(myTopicArn, "Email", "yashi.kamboj@gmail.com");
		//arn:aws:sns:us-west-2:173897362704:cs218
		//Create Pipeline Request
		/*CreatePipelineRequest createPipelineRequest = new CreatePipelineRequest();
        createPipelineRequest.setName("ElasticTranscoderSample");
        createPipelineRequest.setInputBucket("input.yashi.bucket");
        createPipelineRequest.setOutputBucket("output.yashi.bucket");        
        Notifications notifications = new Notifications();
        /*notifications.setWarning("arn:aws:sns:us-west-2:022487948790:SampleTopic");
        notifications.setProgressing("arn:aws:sns:us-west-2:022487948790:SampleTopic");
        notifications.setError("arn:aws:sns:us-west-2:022487948790:SampleTopic");
        notifications.setCompleted("arn:aws:sns:us-west-2:022487948790:SampleTopic")
        createPipelineRequest.setNotifications(notifications);
        createPipelineRequest.setRole("arn:aws:iam::022487948790:role/Elastic_Transcoder_Default_Role");
        		//"arn:aws:iam::7712XXXXXXXX:role/Elastic_Transcoder_Default_Role");
        CreatePipelineResult createPipelineResult = transCoder.createPipeline(createPipelineRequest);
        Pipeline p = createPipelineResult.getPipeline();  
        System.out.println("----------New Pipeline----------");
        System.out.println("Id : "+p.getId());
        PIPELINEID = p.getId();
        System.out.println("Name : "+p.getName());
        System.out.println("Status : "+p.getStatus()); */
		//dispPresetList(transCoder);
		//createCustomPreset(transCoder);
		int flag = createJobEncodeFlvToMp4(transCoder,emailid, ori, res);
	
		return flag;
	}
	
	static AmazonElasticTranscoder init() throws Exception {
		AmazonElasticTranscoder transCoderlocal;
		Properties props = System.getProperties();
		
		
		
		//change credential
    	props.setProperty("aws.accessKeyId", "AKIAJECRDECGP3WEEHHQ");
    	props.setProperty("aws.secretKey", "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");
        AWSCredentials credentials = new SystemPropertiesCredentialsProvider().getCredentials();
        transCoderlocal = new AmazonElasticTranscoderClient(credentials);
        transCoderlocal.setEndpoint("https://elastictranscoder.us-west-2.amazonaws.com");
        return transCoderlocal;
	}
	/*static AmazonSNSClient snsinit() throws Exception {
		AmazonSNSClient SNSLocal;
		Properties props = System.getProperties();
    	props.setProperty("aws.accessKeyId", "AKIAJECRDECGP3WEEHHQ");
    	props.setProperty("aws.secretKey", "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");
        AWSCredentials credentials = new SystemPropertiesCredentialsProvider().getCredentials();
        SNSLocal = new AmazonSNSClient(credentials);
        SNSLocal.setEndpoint("https://elastictranscoder.us-west-2.amazonaws.com");
		return new AmazonSNSClient();
	}*/

	public static int createJobEncodeFlvToMp4(
			AmazonElasticTranscoder transCoder, String emailid, String ori, String res) throws InterruptedException {
		System.out.println("---------- Create Job ----------");
		CreateJobRequest createJobRequest = new CreateJobRequest();
		createJobRequest.setPipelineId(PIPELINEID);
		JobInput input = new JobInput();
		
		
		
		//change to user file
		input.setKey(ori);
		input.setAspectRatio("auto");
		input.setContainer("auto");
		input.setFrameRate("auto");
		input.setInterlaced("auto");
		input.setResolution("auto");
		createJobRequest.setInput(input);
		//createJobRequest.setOutputKeyPrefix("p-transoder/");

		List<CreateJobOutput> outputs = new ArrayList<CreateJobOutput>();
		CreateJobOutput output1 = new CreateJobOutput();
		
		
		
		//change to user output
		output1.setKey(res);
		output1.setPresetId(PRESETID);
//		output1.setThumbnailPattern("thumb-{count}");
		output1.setThumbnailPattern(res.split("\\.")[0]+"-{count}");
		output1.setRotate("auto");

		outputs.add(output1);

		createJobRequest.setOutputs(outputs);

		CreateJobResult createJobResult = transCoder
				.createJob(createJobRequest);
		com.amazonaws.services.elastictranscoder.model.Job job = createJobResult
				.getJob();
		System.out.println("Name : " + job.getStatus());
		System.out.println("Id : " + job.getId());
		System.out.println("Output : " + job.getOutput());
		if(job.getOutput()!=null){
			sendMailToUser(emailid, ori, res);
			return 1;
		}
		return 0;
		/*ReadJobRequest readJobRequest = new ReadJobRequest();
		readJobRequest.setId(job.getId());
	    ReadJobResult result= transCoder.readJob(readJobRequest);
	    Job job1 = result.getJob();

        JobOutput jobOutput = job1.getOutput();

        String status = jobOutput.getStatus();
        System.out.println(status);*/
	    
		/*while(job.getStatus().toUpperCase()!="SUBMITTED"||job.getStatus().toUpperCase()=="PROGRESSING"){
			
		}
		if(job.getStatus().toUpperCase() == "COMPLETED") {
			sendMailToUser();
		}*/
//		System.out.println("Job Complete!!");
	}
	public static void sendMailToUser(String emailid, String ori, String res){
		Destination destination = new Destination().withToAddresses(new String[]{emailid});
		Content subject = new Content().withData("File is ready for download");
        Content textBody = new Content().withData("Your request conversion from" + ori + "to" + res + "is completed."); 
        Body body = new Body().withText(textBody);
     // Create a message with the specified subject and body.
        Message message = new Message().withSubject(subject).withBody(body);
     // Assemble the email.
        
        SendEmailRequest request = new SendEmailRequest().withSource(FROM).withDestination(destination).withMessage(message);
        try
        {        
            System.out.println("Attempting to send an email through Amazon SES by using the AWS SDK for Java...");
            //initializing the simple email service client
            Properties props = System.getProperties();
        	props.setProperty("aws.accessKeyId", "AKIAJECRDECGP3WEEHHQ");
        	props.setProperty("aws.secretKey", "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");
            AWSCredentials credentials = new SystemPropertiesCredentialsProvider().getCredentials();
            AmazonSimpleEmailServiceClient client = new AmazonSimpleEmailServiceClient(credentials);
            
            Region REGION = Region.getRegion(Regions.US_WEST_2);
            
            client.setRegion(REGION);
            /*VerifyEmailAddressRequest verify = new VerifyEmailAddressRequest();
            verify.withEmailAddress(TO);
            client.verifyEmailAddress(verify);*/

            client.sendEmail(request);  
            System.out.println("Email sent!");
            
        }catch(Exception ex) 
        {
            System.out.println("The email was not sent.");
            System.out.println("Error message: " + ex.getMessage());
        }
	}
}