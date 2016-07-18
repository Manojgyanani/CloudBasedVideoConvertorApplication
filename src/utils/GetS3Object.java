package utils;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.S3Object;

public class GetS3Object {
	
	public static float getObjectSize(String fileName) {
		AWSCredentials credentials = new BasicAWSCredentials("AKIAJECRDECGP3WEEHHQ", "eyvNViYlKg9K/kUvBp/XDBR/U8XI0v2eGmyngJPF");
        AmazonS3 s3Client = new AmazonS3Client(credentials);
        String bucketName = "output.yashi.bucket"; 
        float size = 0;
        try {
            System.out.println("Downloading an object");
            S3Object s3object = s3Client.getObject(new GetObjectRequest(
            		bucketName, fileName));
            System.out.println("Content-Type: "  + 
            		s3object.getObjectMetadata().getContentType());

            size = s3object.getObjectMetadata().getContentLength() /1024/1024;
            
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
		return size;
    }
}