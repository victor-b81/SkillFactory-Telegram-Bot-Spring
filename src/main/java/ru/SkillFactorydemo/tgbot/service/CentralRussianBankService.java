/**
 * CentralRussianBankService.java: сервисный класс предназначенный для работы с CbrAPI.
 * Данный класс наследуется от WebServiceTemplate, что предоставляет удобный способ работы с сервисами SOAP.
 * И включает в себя один метод getCurrenciesFromCbr(), который возвращает курсы валют с сайта ЦБР.
 */

package ru.SkillFactorydemo.tgbot.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.ws.client.core.WebServiceTemplate;
import ru.SkillFactorydemo.tgbot.dto.GetCursOnDateXml;
import ru.SkillFactorydemo.tgbot.dto.GetCursOnDateXmlResponse;
import ru.SkillFactorydemo.tgbot.dto.ValuteCursOnDate;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

public class CentralRussianBankService extends WebServiceTemplate {
    @Value("${cbr.api.url}")  // Аннотация Spring, получаем из файла application.properties, api url ссылку - сайта ЦБР
    private String cbrApiUrl;

    // Данный метод возвращает курсы валют ЦБР на заданную дату
    public List<ValuteCursOnDate> getCurrenciesFromCbr() throws DatatypeConfigurationException {
        final GetCursOnDateXml getCursOnDateXML = new GetCursOnDateXml();
        GregorianCalendar cal = new GregorianCalendar();
        cal.setTime(new Date());

        XMLGregorianCalendar xmlGregCal = DatatypeFactory.newInstance().newXMLGregorianCalendar(cal);
        getCursOnDateXML.setOnDate(xmlGregCal);

        GetCursOnDateXmlResponse response = (GetCursOnDateXmlResponse) marshalSendAndReceive(cbrApiUrl, getCursOnDateXML);

        if (response == null) {
            throw new IllegalStateException("Нет ответа от сервиса ЦБР");
        }

        final List<ValuteCursOnDate> courses = response.getGetCursOnDateXmlResult().getValuteData();
        courses.forEach(course -> course.setName(course.getName().trim()));
        return courses;
    }
}