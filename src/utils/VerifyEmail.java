package utils;

import java.util.Properties;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.SystemPropertiesCredentialsProvider;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.simpleemail.AmazonSimpleEmailServiceClient;
import com.amazonaws.services.simpleemail.model.VerifyEmailAddressRequest;

public class VerifyEmail {
	public static void verifyEmail(String email){
		Properties props = System.getProperties();
	    props.setProperty("aws.accessKeyId", "AKIAJUAV6XBF2FL7M2FA");
	    props.setProperty("aws.secretKey", "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");
	    AWSCredentials credentials = new SystemPropertiesCredentialsProvider().getCredentials();
	    AmazonSimpleEmailServiceClient client = new AmazonSimpleEmailServiceClient(credentials);
	    Region REGION = Region.getRegion(Regions.US_WEST_2);
        
        client.setRegion(REGION);
	    VerifyEmailAddressRequest verify = new VerifyEmailAddressRequest();
	    verify.withEmailAddress(email);
	    client.verifyEmailAddress(verify);	
	}

}
