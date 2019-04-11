package net.ukr.vlsv.ippon_secretar.ippon_api.ocrsdk_api_abbyy

import androidx.annotation.XmlRes
import com.google.gson.annotations.SerializedName
import com.squareup.moshi.JsonClass
import org.simpleframework.xml.Attribute
import org.simpleframework.xml.Element
import org.simpleframework.xml.ElementList
import org.simpleframework.xml.Root
import retrofit2.http.Headers
import javax.xml.bind.annotation.XmlAccessorType
import javax.xml.bind.annotation.XmlAttribute
import javax.xml.bind.annotation.XmlElement
import javax.xml.bind.annotation.XmlRootElement


//@JsonClass(generateAdapter = true)
//data class ConstantsResponse (
//        val value: List<ConstantValue>
//)

//@Headers("Content-Type: application/json")
@JsonClass(generateAdapter = true)
data class ProcessImageResponse(
        val ErrorMessage: String,
//        val OutputInformation: String,
        val AvailablePages: Int
//        val ProcessedPages: String,
//        val OCRText: String,
//        val OutputFileUrl: String,
//        val OutputFileUrl2: String,
//        val OutputFileUrl3: String,
//        val TaskDescription: String
)

//data class OCRText(
//
//)
//data class Section @JvmOverloads constructor( @field:Element(name = "id") @param:Element(name = "id") val id: Long, @field:Attribute(name = "title", required = false) @param:Attribute(name = "title", required = false) val title: String = "" )

//@Root(name="response", strict = false) //(generateAdapter = true)
//data class ProcessImageResponse @JvmOverloads constructor(
//    @field:Element(name = "task")
//    @param:Element(name = "task")
//    public val task: Task
////    @Element(name = "task")
////    val task: TaskProcessImage
//)
//{
//    init {
//        val task = task
//    }
//}

//@JsonClass(generateAdapter = true)
//@Root(name="task", strict = false) //(generateAdapter = true)
//data class Task @JvmOverloads constructor(
//    @field:Attribute(name = "id")
//    @param:Attribute(name = "id")
//    val id: String,
//    @field:Attribute(name = "credits")
//    val credits: String,
//    @field:Attribute(name = "filesCount")
//    val filesCount: String,
//    @field:Attribute(name = "status")
//    val status: String,
//    @field:Attribute(name = "statusChangeTime")
//    val statusChangeTime: String,
//    @field:Attribute(name = "registrationTime")
//    val registrationTime: String
//)
//{
//    init {
//        val id = id;
//        val credits = credits;
//        val filesCount = filesCount;
//        val status = status;
//        val statusChangeTime = statusChangeTime;
//        val registrationTime = registrationTime
//
//    }
//}

//data class Task(
//    val task: TaskProcessImage
//)

//data class TaskProcessImage (
//        @Element(name = "id")
//        val id: String
//)

//@Root(name="task") //(generateAdapter = true)
////@XmlAccessorType(XmlAccessType.FIELD)
//data class Task (
//    @Attribute(name = "id")
//    val id: String
//////    @XmlAttribute(name = "credits")
////    val credits: Int,
//////    @XmlAttribute(name = "filesCount")
////    val filesCount: String ,
//////    @XmlAttribute(name = "status")
////    val status: String ,
//////    @XmlAttribute(name = "statusChangeTime")
////    val statusChangeTime: String ,
//////    @XmlAttribute(name = "registrationTime")
////    val registrationTime: String
//)
//
////@XmlRootElement
//@XmlRootElement(name="response")
////@XmlAccessorType(XmlAccessType.FIELD)
//data class ProcessImageResponse (
////    @Element(name = "task")
//    val task: Task //= Task("")
//
////    ProcessImageResponse() {}
//)
