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
package egovframework.grgrowth.main.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import egovframework.grgrowth.main.service.MainService;
import egovframework.rte.fdl.idgnr.EgovIdGnrService;

/**
 * 메인 CRUD 요청을 처리하는 비즈니스 클래스
 */
@Service("mainService")
public class MainServiceImpl implements MainService {

	@Resource(name = "mainMapper")
	private MainMapper mainMapper;

	@Resource(name = "egovIdGnrServiceEmp")
	private EgovIdGnrService egovIdGnrService;
}