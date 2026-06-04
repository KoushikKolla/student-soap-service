package com.example.student_soap_service.config;

import com.example.student_soap_service.interceptor.ClientHeaderInterceptor;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.ws.config.annotation.EnableWs;
import org.springframework.ws.config.annotation.WsConfigurer;
import org.springframework.ws.server.EndpointInterceptor;
import org.springframework.ws.transport.http.MessageDispatcherServlet;
import org.springframework.xml.xsd.SimpleXsdSchema;
import org.springframework.xml.xsd.XsdSchema;
import org.springframework.ws.wsdl.wsdl11.DefaultWsdl11Definition;

import java.util.List;

@EnableWs
@Configuration
public class SoapConfig implements WsConfigurer {

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
    @Override
    public void addInterceptors(
            List<EndpointInterceptor> interceptors) {

        interceptors.add(
                new ClientHeaderInterceptor());
    }
}