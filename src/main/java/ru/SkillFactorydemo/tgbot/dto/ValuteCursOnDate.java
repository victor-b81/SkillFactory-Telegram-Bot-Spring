/**
 * ValuteCursOnDate.java: часть слоя DTO для работы с API ЦБ РФ.
 * Данный слой DTO передает поля: name(Название), nominal(Номинал), course(КурсВалюты), code(ЦифровойКод),
 * chCode(БуквенныйКод) в XML формате.
 */

package ru.SkillFactorydemo.tgbot.dto;

import lombok.Data;

import javax.xml.bind.annotation.*;

@XmlRootElement(name = "ValuteCursOnDate")    // Аннотация JAXB, указываем корневой элемент XML
@Data   // Аннотация Lombok, генерируем геттеры и сеттеры
@XmlAccessorType(XmlAccessType.FIELD)   // Аннотация JAXB, указываем сериализацию полей XML
public class ValuteCursOnDate {

    @XmlElement(name = "Vname")    // Аннотация JAXB, указываем элемент XML, Vname(Название)
    private String name;

    @XmlElement(name = "Vnom")    // Аннотация JAXB, указываем элемент XML, Vnom(Номинал)
    private int nominal;

    @XmlElement(name = "Vcurs")    // Аннотация JAXB, указываем элемент XML, Vcurs(КурсВалюты)
    private double course;

    @XmlElement(name = "Vcode")    // Аннотация JAXB, указываем элемент XML, Vcode(ЦифровойКод)
    private String code;

    @XmlElement(name = "VchCode")    // Аннотация JAXB, указываем элемент XML, VchCode(БуквенныйКод)
    private String chCode;
}