/**
 * GetCursOnDateXml.java: часть слоя DTO для работы с API ЦБ РФ.
 * Данный слой DTO передает объект запроса onDate "дата" в XML формате
 */

package ru.SkillFactorydemo.tgbot.dto;

import lombok.Data;

import javax.xml.bind.annotation.*;
import javax.xml.datatype.XMLGregorianCalendar;


@XmlRootElement(name = "GetCursOnDateXML", namespace = "http://web.cbr.ru/")    // Аннотация JAXB, указываем корневой элемент XML
@Data   // Аннотация Lombok, генерируем геттеры и сеттеры
@XmlAccessorType(XmlAccessType.FIELD)   // Аннотация JAXB, указываем сериализацию полей XML
public class GetCursOnDateXml {

    @XmlElement(name = "On_date", required = true, namespace = "http://web.cbr.ru/")    // Аннотация JAXB, указываем элемент XML
    protected XMLGregorianCalendar onDate;
}