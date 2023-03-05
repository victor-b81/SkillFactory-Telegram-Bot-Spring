/**
 * GetCursOnDateXmlResponse.java: часть слоя DTO для работы с API ЦБ РФ.
 * Данный слой DTO передает объект данные ответа getCursOnDateXmlResult в XML формате
 */

package ru.SkillFactorydemo.tgbot.dto;

import lombok.Data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "GetCursOnDateXMLResponse", namespace = "http://web.cbr.ru/")    // Аннотация JAXB, указываем корневой элемент XML
@Data   // Аннотация Lombok, генерируем геттеры и сеттеры
@XmlAccessorType(XmlAccessType.FIELD)   // Аннотация JAXB, указываем сериализацию полей XML
public class GetCursOnDateXmlResponse {

    @XmlElement(name = "GetCursOnDateXMLResult", namespace = "http://web.cbr.ru/")    // Аннотация JAXB, указываем элемент XML
    private GetCursOnDateXmlResult getCursOnDateXmlResult;
}