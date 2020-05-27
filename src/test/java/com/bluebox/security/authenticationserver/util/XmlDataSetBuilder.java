package com.bluebox.security.authenticationserver.util;

import org.dbunit.dataset.DataSetException;
import org.dbunit.dataset.xml.XmlDataSet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.InputStream;

public final class XmlDataSetBuilder {
    private static final Logger logger = LoggerFactory.getLogger(XmlDataSetBuilder.class);

    public XmlDataSet build(InputStream xmlInputStream) throws DataSetException {
        return buildInternal(xmlInputStream);
    }




    private XmlDataSet buildInternal(InputStream inputSource) throws DataSetException {
        logger.trace("build(inputSource={}) - start", inputSource);

        // Validate required parameters
        if (inputSource == null) {
            throw new NullPointerException("The parameter 'inputSource' must not be null");
        }
        return new XmlDataSet(inputSource);
    }



}