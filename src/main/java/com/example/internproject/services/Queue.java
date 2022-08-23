package com.example.internproject.services;

import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.sns.AmazonSNS;
import com.amazonaws.services.sns.AmazonSNSClientBuilder;
import com.amazonaws.services.sns.model.PublishRequest;
import com.amazonaws.services.sns.model.PublishResult;
import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.AmazonSQSClientBuilder;
import com.amazonaws.services.sqs.model.*;

import java.util.ArrayList;
import java.util.List;

public class Queue {
    static AmazonSQS sqs= AmazonSQSClientBuilder.standard().withEndpointConfiguration(new AwsClientBuilder.EndpointConfiguration("http://localhost:4566", "us-east-2")).build();

    static String queueUrl="http://localhost:4566/000000000000/food_queue";

    public static void PublishTopic() {
        AmazonSNS snsClient = AmazonSNSClientBuilder.standard()
                .withEndpointConfiguration(new AwsClientBuilder.EndpointConfiguration("http://localhost:4566", "us-east-2"))
                .build();
        final String msg = "Hey, this is second check message, Saad Ali!!";
        final PublishRequest publishRequest = new PublishRequest("arn:aws:sns:us-east-2:000000000000:topic1", msg);
        final PublishResult publishResponse = snsClient.publish((publishRequest));

// Print the MessageId of the message.
        System.out.println("Your message has been published to the given topic :)");
        System.out.println("MessageId: " + publishResponse.getMessageId());
    }

    public static void SendMessage(String msg){
        try {
            SendMessageRequest send_msg_req = new SendMessageRequest().withQueueUrl(queueUrl)
                    .withMessageBody(msg)
                    .withDelaySeconds(0);
            SendMessageResult send_msg_rslt = sqs.sendMessage(send_msg_req);
            System.out.println("Message Sent Successfully!");
        }
        catch(Exception e) {
            System.out.println("Failed");
        }

    }

//    public static void ReadMessage(){
//        System.out.println("Reading Messages From Queue");
//        // Integer i = new ReceiveMessageRequest().withQueueUrl(queueUrl).getMaxNumberOfMessages();
//
//        ReceiveMessageRequest req=new ReceiveMessageRequest(queueUrl).withWaitTimeSeconds(10);
//        //req.setMaxNumberOfMessages(10);
//        //List<Message> messages=sqs.receiveMessage(req).getMessages();
//        int ii=0;
//        //System.out.println(messages.get(0).getBody());
//        try {
//            for (int i = 0; i < 10; i++) {
//                List<Message> messages = sqs.receiveMessage(req).getMessages();
//                System.out.println(messages.get(0).toString());
//                ii++;
//            }
//        }
//        catch(Exception e)
//        {
//            System.out.println("QUeue is emptyy");
//        }
//        System.out.println("Count: " + ii);
//        System.out.println("Queue Scanned Successfully!");
//    }

    public static List<Message> ReadMessage() throws InterruptedException {
        System.out.println("Reading Messages From Queue");
        // Integer i = new ReceiveMessageRequest().withQueueUrl(queueUrl).getMaxNumberOfMessages();



        ReceiveMessageRequest req=new ReceiveMessageRequest(queueUrl).withMaxNumberOfMessages(30).withVisibilityTimeout(1);
        List<Message> messages=new ArrayList<>();
        try{
        Thread.sleep(1500);
         messages=sqs.receiveMessage(req).getMessages();



            int ii= messages.size();
            for(int i = 0;i<ii;i++) {
                System.out.println("forloop");
//                List<Message> messages = sqs.receiveMessage(req).getMessages();
                System.out.println(messages.get(i).getBody().toString());
                //ii++;
            }
        }
        catch(Exception e)
        {

            //System.out.println("Count: " + ii);
            System.out.println("Queue Scanned Successfully!");
        }

        return messages;
    }

    //for reading 1 message
    public static String ReadFirstMessage() {
        System.out.println("Reading Messages From Queue");
        // Integer i = new ReceiveMessageRequest().withQueueUrl(queueUrl).getMaxNumberOfMessages();

        ReceiveMessageRequest req = new ReceiveMessageRequest(queueUrl).withMaxNumberOfMessages(30).withVisibilityTimeout(1);
        String message="";
        try {
            List<Message> messages = sqs.receiveMessage(req).getMessages();

            message = messages.get(0).getBody();


        } catch (Exception e) {
            System.out.println(e.toString());
        }
        return message;
    }
    public static void DeleteMessage(){
        ReceiveMessageRequest req=new ReceiveMessageRequest().withQueueUrl(queueUrl).withVisibilityTimeout(10);
        //List<Message> messages=sqs.receiveMessage(req).getMessages();
        //DeleteMessageResult res= sqs.deleteMessage(queueUrl,messages.get(0).getReceiptHandle());
        for (int i=0;i<10;i++){
            System.out.println("Deleting Message!");

            List<Message> messages=sqs.receiveMessage(req).getMessages();
            DeleteMessageResult res= sqs.deleteMessage(queueUrl,messages.get(0).getReceiptHandle());
            System.out.println("Message Deleted Successfully!");
        }
    }

    //for deleting 1 message

    public static void DeleteFirstMessage(){

        //List<Message> messages=sqs.receiveMessage(req).getMessages();
        //DeleteMessageResult res= sqs.deleteMessage(queueUrl,messages.get(0).getReceiptHandle());
            System.out.println("Deleting Message!");
        try {
            List<Message> messages = Queue.ReadMessage();

                DeleteMessageResult res = sqs.deleteMessage(queueUrl, messages.get(0).getReceiptHandle());
                System.out.println("Message Deleted Successfully!");
            }
            catch (Exception exception){
                System.out.println(exception.toString());
        }
        }


    public static void main(String[] args) {

       // SendMessage("Hello hotel key");
        //SendMessage("Hello hotel");
        //SendMessage("Hello hotel....");
        //ReadMessage();

        //ReadMessage();
        //DeleteMessage();
        //ReadMessage();
        //PublishTopic();
    }
}