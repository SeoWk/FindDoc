package com.seo.finddoc.json_entity

import com.google.gson.annotations.SerializedName

data class HospitalItem (
  @SerializedName("addr"           ) var addr           : String = "",
  @SerializedName("clCd"           ) var clCd           : String = "",
  @SerializedName("clCdNm"         ) var clCdNm         : String = "",
  @SerializedName("cmdcGdrCnt"     ) var cmdcGdrCnt     : Int = -1,
  @SerializedName("cmdcIntnCnt"    ) var cmdcIntnCnt    : Int = -1,
  @SerializedName("cmdcResdntCnt"  ) var cmdcResdntCnt  : Int = -1,
  @SerializedName("cmdcSdrCnt"     ) var cmdcSdrCnt     : Int = -1,
  @SerializedName("detyGdrCnt"     ) var detyGdrCnt     : Int = -1,
  @SerializedName("detyIntnCnt"    ) var detyIntnCnt    : Int = -1,
  @SerializedName("detyResdntCnt"  ) var detyResdntCnt  : Int = -1,
  @SerializedName("detySdrCnt"     ) var detySdrCnt     : Int = -1,
  @SerializedName("drTotCnt"       ) var drTotCnt       : Int = -1,
  @SerializedName("emdongNm"       ) var emdongNm       : String = "",
  @SerializedName("estbDd"         ) var estbDd         : Int = -1,
  @SerializedName("hospUrl"        ) var hospUrl        : String = "",
  @SerializedName("mdeptGdrCnt"    ) var mdeptGdrCnt    : Int = -1,
  @SerializedName("mdeptIntnCnt"   ) var mdeptIntnCnt   : Int = -1,
  @SerializedName("mdeptResdntCnt" ) var mdeptResdntCnt : Int = -1,
  @SerializedName("mdeptSdrCnt"    ) var mdeptSdrCnt    : Int = -1,
  @SerializedName("pnursCnt"       ) var pnursCnt       : Int = -1,
  @SerializedName("postNo"         ) var postNo         : Int = -1,
  @SerializedName("sgguCd"         ) var sgguCd         : Int = -1,
  @SerializedName("sgguCdNm"       ) var sgguCdNm       : String = "",
  @SerializedName("sidoCd"         ) var sidoCd         : Int = -1,
  @SerializedName("sidoCdNm"       ) var sidoCdNm       : String = "",
  @SerializedName("telno"          ) var telno          : String = "",
  @SerializedName("XPos"           ) var XPos           : Double = 0.0,
  @SerializedName("YPos"           ) var YPos           : Double = 0.0,
  @SerializedName("yadmNm"         ) var yadmNm         : String = "",
  @SerializedName("ykiho"          ) var ykiho          : String = ""
)