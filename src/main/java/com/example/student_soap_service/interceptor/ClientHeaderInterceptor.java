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
    public boolean handleRequest(MessageContext messageContext, Object endpoint) throws Exception {
        String clientId = null;
        String requestId = null;

        SoapMessage soapMessage = (SoapMessage) messageContext.getRequest();
        Source source =
                soapMessage.getPayloadSource();

        System.out.println(
                "Payload Source = "
                        + source);
        System.out.println(
                "Endpoint = "
                        + endpoint.getClass().getSimpleName());
        SoapHeader soapHeader = soapMessage.getSoapHeader();


        Iterator<SoapHeaderElement> iterator = soapHeader.examineAllHeaderElements();

        while (iterator.hasNext()) {

            SoapHeaderElement element = iterator.next();

            String localPart = element.getName().getLocalPart();

            if ("clientId".equals(localPart)) {

                clientId = element.getText();
            }

            if ("requestId".equals(localPart)) {

                requestId = element.getText();
            }
        }
        if (clientId == null || clientId.isBlank()) {

            throw new RuntimeException("Client Id is required");
        }
        System.out.println(
                "Client Id : " + clientId);

        System.out.println(
                "Request Id : " + requestId);
        System.out.println(
                "Time : "
                        + LocalDateTime.now());


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
