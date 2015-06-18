/*
 * Copyright 2011 MOPAS(Ministry of Public Administration and Security).
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package egovframework.grgrowth.main.service;


/**
 * 지역날씨 정보를 저장하기 위한 VO클래스
 */
public class WeatherVO {
	
    /** 지역 */
	private String local;
	
	/** ? */
	private String stn_id;
	
	/** ? */
	private String icon;
	
	/** 내용 */
	private String desc;
	
	/** 현재기온 */
	private String ta;
	
	/** ? */
	private String rn_hr1;

    public String getLocal() {
        return local;
    }

    public void setLocal(String local) {
        this.local = local;
    }

    public String getStn_id() {
        return stn_id;
    }

    public void setStn_id(String stn_id) {
        this.stn_id = stn_id;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getTa() {
        return ta;
    }

    public void setTa(String ta) {
        this.ta = ta;
    }

    public String getRn_hr1() {
        return rn_hr1;
    }

    public void setRn_hr1(String rn_hr1) {
        this.rn_hr1 = rn_hr1;
    }
}
