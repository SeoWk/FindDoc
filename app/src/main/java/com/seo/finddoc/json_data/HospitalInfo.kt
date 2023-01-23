package com.seo.finddoc.json_data

import com.google.gson.annotations.SerializedName

/**
 * 병원에 대한 정보들
 */
data class HospitalRoot(
    @SerializedName("response")
    var response: MainJSON
)
data class MainJSON(
    @SerializedName("header")
    var header: Header,
    @SerializedName("body")
    var body: Body
)
data class Header(
    @SerializedName("resultCode")
    var resultCode: String = "",
    @SerializedName("resultMsg")
    var resultMsg: String = ""
)
data class Body(
    @SerializedName("items")
    var items: ItemInfo,
    @SerializedName("numOfRows")
    var numOfRows: Int = -1,
    @SerializedName("pageNo")
    var pageNo: Int = -1,
    @SerializedName("totalCount")
    var totalCount: Int = -1
)
data class ItemInfo(
    @SerializedName("item")
    var item: MutableList<HospitalItem> = mutableListOf()
)

data class HospitalItem(
   /* @SerializedName("dutyAddr") var dutyAddr : String = "",
    @SerializedName("dutyDiv") var dutyDiv : String = "",
    @SerializedName("dutyDivNam") var dutyDivNam : String = "",
    @SerializedName("dutyEmcls") var dutyEmcls : String = "",
    @SerializedName("dutyEmclsName") var dutyEmclsName : String = "",
    @SerializedName("dutyEryn") var dutyEryn : Int = -1,
    @SerializedName("dutyName") var dutyName : String = "",
    @SerializedName("dutyTel1") var dutyTel1 : String = "",
    @SerializedName("dutyTime1c") var dutyTime1c: Int = -1,
    @SerializedName("dutyTime1s") var dutyTime1s: Int = -1,
    @SerializedName("dutyTime2c") var dutyTime2c: Int = -1,
    @SerializedName("dutyTime2s") var dutyTime2s: Int = -1,
    @SerializedName("dutyTime3c") var dutyTime3c: Int = -1,
    @SerializedName("dutyTime3s") var dutyTime3s: Int = -1,
    @SerializedName("dutyTime4c") var dutyTime4c: Int = -1,
    @SerializedName("dutyTime4s") var dutyTime4s: Int = -1,
    @SerializedName("dutyTime5c") var dutyTime5c: Int = -1,
    @SerializedName("dutyTime5s") var dutyTime5s: Int = -1,
    @SerializedName("dutyTime6c") var dutyTime6c: Int = -1,
    @SerializedName("dutyTime6s") var dutyTime6s: Int = -1,
    @SerializedName("dutyTime7c") var dutyTime7c: Int = -1,
    @SerializedName("dutyTime7s") var dutyTime7s: Int = -1,
    @SerializedName("dutyTime8c") var dutyTime8c: Int = -1,
    @SerializedName("dutyTime8s") var dutyTime8s: Int = -1,
    @SerializedName("hpid"          ) var hpid: String = "",
    @SerializedName("postCdn1"      ) var postCdn1: Int = -1,
    @SerializedName("postCdn2"      ) var postCdn2: String = "",
    @SerializedName("rnum"          ) var rnum: Int = -1,
    @SerializedName("wgs84Lat"      ) var wgs84Lat: String = "",
    @SerializedName("wgs84Lon"      ) var wgs84Lon: String = ""*/
    @SerializedName("addr") var addr           : String = "",
    @SerializedName("clCd") var clCd           : String = "",
    @SerializedName("clCdNm") var clCdNm         : String = "",
    @SerializedName("cmdcGdrCnt") var cmdcGdrCnt     : Int = -1,
    @SerializedName("cmdcIntnCnt") var cmdcIntnCnt    : Int = -1,
    @SerializedName("cmdcResdntCnt") var cmdcResdntCnt  : Int = -1,
    @SerializedName("cmdcSdrCnt") var cmdcSdrCnt     : Int = -1,
    @SerializedName("detyGdrCnt") var detyGdrCnt     : Int = -1,
    @SerializedName("detyIntnCnt") var detyIntnCnt    : Int = -1,
    @SerializedName("detyResdntCnt") var detyResdntCnt  : Int = -1,
    @SerializedName("detySdrCnt") var detySdrCnt     : Int = -1,
    @SerializedName("drTotCnt") var drTotCnt       : Int = -1,
    @SerializedName("emdongNm") var emdongNm       : String = "",
    @SerializedName("estbDd") var estbDd         : Int = -1,
    @SerializedName("hospUrl") var hospUrl        : String = "",
    @SerializedName("mdeptGdrCnt") var mdeptGdrCnt    : Int = -1,
    @SerializedName("mdeptIntnCnt") var mdeptIntnCnt   : Int = -1,
    @SerializedName("mdeptResdntCnt") var mdeptResdntCnt : Int = -1,
    @SerializedName("mdeptSdrCnt") var mdeptSdrCnt    : Int = -1,
    @SerializedName("pnursCnt") var pnursCnt       : Int = -1,
    @SerializedName("postNo") var postNo         : Int = -1,
    @SerializedName("sgguCd") var sgguCd         : Int = -1,
    @SerializedName("sgguCdNm") var sgguCdNm       : String = "",
    @SerializedName("sidoCd") var sidoCd         : Int = -1,
    @SerializedName("sidoCdNm") var sidoCdNm       : String = "",
    @SerializedName("telno") var telno          : String = "",
    @SerializedName("XPos") var XPos           : Double = -1.0,
    @SerializedName("YPos") var YPos           : Double = -1.0,
    @SerializedName("yadmNm") var yadmNm         : String = "",
    @SerializedName("ykiho") var ykiho          : String = ""
)