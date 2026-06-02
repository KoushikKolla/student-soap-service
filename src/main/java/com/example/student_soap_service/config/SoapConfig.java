package com.example.student_soap_service.config;

import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.ws.config.annotation.EnableWs;
import org.springframework.ws.transport.http.MessageDispatcherServlet;
import org.springframework.xml.xsd.SimpleXsdSchema;
import org.springframework.xml.xsd.XsdSchema;
import org.springframework.ws.wsdl.wsdl11.DefaultWsdl11Definition;

@EnableWs
@Configuration
public class SoapConfig {

    @Bean
    public ServletRegistrationBean<MessageDispatcherServlet>
    messageDispatcherServlet(
            ApplicationContext context) {

        MessageDispatcherServlet servlet =
                new MessageDispatcherServlet();

        servlet.setApplicationContext(context);

        servlet.setTransformWsdlLocations(true);

        return new ServletRegistrationBean<>(
                servlet,
                "/ws/*");
    }

    @Bean
    public XsdSchema studentSchema() {

        return new SimpleXsdSchema(
                new ClassPathResource(
                        "student.xsd"));
    }

    @Bean(name = "students")
    public DefaultWsdl11Definition
    defaultWsdl11Definition(
            XsdSchema studentSchema) {

        DefaultWsdl11Definition wsdl =
                new DefaultWsdl11Definition();

        wsdl.setPortTypeName("StudentPort");

        wsdl.setLocationUri("/ws");

        wsdl.setTargetNamespace(
                "http://example.com/student");

        wsdl.setSchema(studentSchema);

        return wsdl;
    }
}