package com.example.student_soap_service.interceptor;

import org.springframework.ws.context.MessageContext;
import org.springframework.ws.server.EndpointInterceptor;
import org.springframework.ws.soap.SoapHeader;
import org.springframework.ws.soap.SoapHeaderElement;
import org.springframework.ws.soap.SoapMessage;

import javax.xml.transform.Source;
import java.time.LocalDateTime;
import java.util.Iterator;

public class ClientHeaderInterceptor
        implements EndpointInterceptor {

    @Override
    public boolean handleRequest(
            MessageContext messageContext,
            Object endpoint)
            throws Exception {

        String clientId = null;
        String requestId = null;
        String username = null;
        String password = null;

        SoapMessage soapMessage =
                (SoapMessage) messageContext.getRequest();

        System.out.println(
                "Payload Source = "
                        + soapMessage.getPayloadSource());

        System.out.println(
                "Endpoint = "
                        + endpoint.getClass().getSimpleName());

        SoapHeader soapHeader =
                soapMessage.getSoapHeader();

        Iterator<SoapHeaderElement> iterator =
                soapHeader.examineAllHeaderElements();

        while (iterator.hasNext()) {

            SoapHeaderElement element =
                    iterator.next();

            String localPart =
                    element.getName().getLocalPart();

            String value =
                    element.getText();

            switch (localPart) {

                case "clientId":
                    clientId = value;
                    break;

                case "requestId":
                    requestId = value;
                    break;

                case "username":
                    username = value;
                    break;

                case "password":
                    password = value;
                    break;
            }
        }

        // Client Validation

        if (clientId == null || clientId.isBlank()) {
            throw new RuntimeException(
                    "Client Id is required");
        }

        // Authentication Validation

        if (username == null || username.isBlank()) {
            throw new RuntimeException(
                    "Username is required");
        }

        if (password == null || password.isBlank()) {
            throw new RuntimeException(
                    "Password is required");
        }

        if (!"admin".equals(username)
                || !"admin123".equals(password)) {

            throw new RuntimeException(
                    "Invalid Username or Password");
        }

        System.out.println(
                "\n====================================");

        System.out.println(
                "SOAP REQUEST RECEIVED");

        System.out.println(
                "Client Id : "
                        + clientId);

        System.out.println(
                "Request Id : "
                        + requestId);

        System.out.println(
                "Username : "
                        + username);

        System.out.println(
                "Endpoint : "
                        + endpoint.getClass().getSimpleName());

        System.out.println(
                "Time : "
                        + LocalDateTime.now());

        System.out.println(
                "====================================\n");

        return true;
    }

    @Override
    public boolean handleResponse(
            MessageContext messageContext,
            Object endpoint)
            throws Exception {

        System.out.println(
                "SOAP RESPONSE SENT");

        return true;
    }

    @Override
    public boolean handleFault(
            MessageContext messageContext,
            Object endpoint)
            throws Exception {

        return true;
    }

    @Override
    public void afterCompletion(
            MessageContext messageContext,
            Object endpoint,
            Exception ex)
            throws Exception {

    }
}
