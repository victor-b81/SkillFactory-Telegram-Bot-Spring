/**
 * GetCursOnDateXmlResult.java: часть слоя DTO для работы с API ЦБ РФ.
 * Данный слой DTO передает объект-колекцию ответа, valuteData, содержащую поля ValuteCursOnDate: name(Название),
 * nominal(Номинал), course(КурсВалюты), code(ЦифровойКод), chCode(БуквенныйКод) в XML формате
 */

package ru.SkillFactorydemo.tgbot.dto;

import lombok.Data;

import javax.xml.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;

@XmlRootElement(name = "GetCursOnDateXmlResult")    // Аннотация JAXB, указываем корневой элемент XML
@Data   // Аннотация Lombok, генерируем геттеры и сеттеры
@XmlAccessorType(XmlAccessType.FIELD)   // Аннотация JAXB, указываем сериализацию полей XML
public class GetCursOnDateXmlResult {

    @XmlElementWrapper(name = "ValuteData", namespace = "")    // Аннотация JAXB, указываем группу элементов XML
    @XmlElement(name = "ValuteCursOnDate", namespace = "")    // Аннотация JAXB, указываем элемент XML
    private List<ValuteCursOnDate> valuteData = new ArrayList<>();
}