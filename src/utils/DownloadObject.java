package utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.S3Object;

public class DownloadObject {
	private static String bucketName = "cs218team4"; 
	private static String key        ;      
	
	public static void download(String path, String fileName, String to) throws IOException {
        AmazonS3 s3Client = new AmazonS3Client(new ProfileCredentialsProvider());
        try {
            System.out.println("Downloading an object");
            key = fileName;
            S3Object s3object = s3Client.getObject(new GetObjectRequest(
            		bucketName, key));
            System.out.println("Content-Type: "  + 
            		s3object.getObjectMetadata().getContentType());
            displayTextInputStream(s3object.getObjectContent(),path,fileName, to);
            
           // Get a range of bytes from an object.
            
//            GetObjectRequest rangeObjectRequest = new GetObjectRequest(
//            		bucketName, key);
//            rangeObjectRequest.setRange(0, 10);
//            S3Object objectPortion = s3Client.getObject(rangeObjectRequest);
//            
//            System.out.println("Printing bytes retrieved.");
//            displayTextInputStream(objectPortion.getObjectContent(),path);
            
        } catch (AmazonServiceException ase) {
            System.out.println("Caught an AmazonServiceException, which" +
            		" means your request made it " +
                    "to Amazon S3, but was rejected with an error response" +
                    " for some reason.");
            System.out.println("Error Message:    " + ase.getMessage());
            System.out.println("HTTP Status Code: " + ase.getStatusCode());
            System.out.println("AWS Error Code:   " + ase.getErrorCode());
            System.out.println("Error Type:       " + ase.getErrorType());
            System.out.println("Request ID:       " + ase.getRequestId());
        } catch (AmazonClientException ace) {
            System.out.println("Caught an AmazonClientException, which means"+
            		" the client encountered " +
                    "an internal error while trying to " +
                    "communicate with S3, " +
                    "such as not being able to access the network.");
            System.out.println("Error Message: " + ace.getMessage());
        }
    }

    private static void displayTextInputStream(InputStream input, String path, String fileName, String to)
    throws IOException {
    	// Read one text line at a time and display.
//        BufferedReader reader = new BufferedReader(new 
//        		InputStreamReader(input));
    	FileOutputStream outputStream = null;
    	String filepath = path + "\\" + fileName.split(".")[0] + "." + to;
        outputStream = new FileOutputStream(new File(filepath));
        int read = 0;
		while ((read = input.read()) != -1) {
			outputStream.write(read);
		}
 
		System.out.println("Done!");
        System.out.println();
        outputStream.close();
    }
}
