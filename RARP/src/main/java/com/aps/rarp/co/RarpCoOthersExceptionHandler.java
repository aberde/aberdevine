package com.aps.rarp.co;

import org.apache.log4j.Logger;

import egovframework.rte.fdl.cmmn.exception.handler.ExceptionHandler;

public class RarpCoOthersExceptionHandler implements ExceptionHandler {

    private static final Logger LOGGER = Logger.getLogger(RarpCoOthersExceptionHandler.class);

    public void occur(Exception exception, String packageName) {
        LOGGER.debug(" CmsCoOthersExceptionHandler run...............");
    }

}
