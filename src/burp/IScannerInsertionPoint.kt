package burp

/*
 * @(#)IScannerInsertionPoint.java
 *
 * Copyright PortSwigger Ltd. All rights reserved.
 *
 * This code may be used to extend the functionality of Burp Suite Community Edition
 * and Burp Suite Professional, provided that this usage does not violate the
 * license terms for those products.
 */
/**
 * This interface is used to define an insertion point for use by active Scanner
 * checks. Extensions can obtain instances of this interface by registering an
 * `IScannerCheck`, or can create instances for use by Burp's own
 * scan checks by registering an
 * `IScannerInsertionPointProvider`.
 */
interface IScannerInsertionPoint {

    /**
     * This method returns the name of the insertion point.
     *
     * @return The name of the insertion point (for example, a description of a
     * particular request parameter).
     */
    val insertionPointName: String

    /**
     * This method returns the base value for this insertion point.
     *
     * @return the base value that appears in this insertion point in the base
     * request being scanned, or `null` if there is no value in the
     * base request that corresponds to this insertion point.
     */
    val baseValue: String

    /**
     * This method returns the type of the insertion point.
     *
     * @return The type of the insertion point. Available types are defined in
     * this interface.
     */
    val insertionPointType: Byte

    /**
     * This method is used to build a request with the specified payload placed
     * into the insertion point. There is no requirement for extension-provided
     * insertion points to adjust the Content-Length header in requests if the
     * body length has changed, although Burp-provided insertion points will
     * always do this and will return a request with a valid Content-Length
     * header.
     * **Note:**
     * Scan checks should submit raw non-encoded payloads to insertion points,
     * and the insertion point has responsibility for performing any data
     * encoding that is necessary given the nature and location of the insertion
     * point.
     *
     * @param payload The payload that should be placed into the insertion
     * point.
     * @return The resulting request.
     */
    fun buildRequest(payload: ByteArray): ByteArray

    /**
     * This method is used to determine the offsets of the payload value within
     * the request, when it is placed into the insertion point. Scan checks may
     * invoke this method when reporting issues, so as to highlight the relevant
     * part of the request within the UI.
     *
     * @param payload The payload that should be placed into the insertion
     * point.
     * @return An int[2] array containing the start and end offsets of the
     * payload within the request, or null if this is not applicable (for
     * example, where the insertion point places a payload into a serialized
     * data structure, the raw payload may not literally appear anywhere within
     * the resulting request).
     */
    fun getPayloadOffsets(payload: ByteArray): IntArray

    companion object {

        /**
         * Used to indicate where the payload is inserted into the value of a URL
         * parameter.
         */
        val INS_PARAM_URL: Byte = 0x00
        /**
         * Used to indicate where the payload is inserted into the value of a body
         * parameter.
         */
        val INS_PARAM_BODY: Byte = 0x01
        /**
         * Used to indicate where the payload is inserted into the value of an HTTP
         * cookie.
         */
        val INS_PARAM_COOKIE: Byte = 0x02
        /**
         * Used to indicate where the payload is inserted into the value of an item
         * of data within an XML data structure.
         */
        val INS_PARAM_XML: Byte = 0x03
        /**
         * Used to indicate where the payload is inserted into the value of a tag
         * attribute within an XML structure.
         */
        val INS_PARAM_XML_ATTR: Byte = 0x04
        /**
         * Used to indicate where the payload is inserted into the value of a
         * parameter attribute within a multi-part message body (such as the name of
         * an uploaded file).
         */
        val INS_PARAM_MULTIPART_ATTR: Byte = 0x05
        /**
         * Used to indicate where the payload is inserted into the value of an item
         * of data within a JSON structure.
         */
        val INS_PARAM_JSON: Byte = 0x06
        /**
         * Used to indicate where the payload is inserted into the value of an AMF
         * parameter.
         */
        val INS_PARAM_AMF: Byte = 0x07
        /**
         * Used to indicate where the payload is inserted into the value of an HTTP
         * request header.
         */
        val INS_HEADER: Byte = 0x20
        /**
         * Used to indicate where the payload is inserted into a URL path folder.
         */
        val INS_URL_PATH_FOLDER: Byte = 0x21
        /**
         * Used to indicate where the payload is inserted into a URL path folder.
         * This is now deprecated; use `INS_URL_PATH_FOLDER` instead.
         */
        @Deprecated("This is now deprecated; use `INT_URL_PATH_FOLDER` instead.")
        val INS_URL_PATH_REST = INS_URL_PATH_FOLDER
        /**
         * Used to indicate where the payload is inserted into the name of an added
         * URL parameter.
         */
        val INS_PARAM_NAME_URL: Byte = 0x22
        /**
         * Used to indicate where the payload is inserted into the name of an added
         * body parameter.
         */
        val INS_PARAM_NAME_BODY: Byte = 0x23
        /**
         * Used to indicate where the payload is inserted into the body of the HTTP
         * request.
         */
        val INS_ENTIRE_BODY: Byte = 0x24
        /**
         * Used to indicate where the payload is inserted into the URL path
         * filename.
         */
        val INS_URL_PATH_FILENAME: Byte = 0x25
        /**
         * Used to indicate where the payload is inserted at a location manually
         * configured by the user.
         */
        val INS_USER_PROVIDED: Byte = 0x40
        /**
         * Used to indicate where the insertion point is provided by an
         * extension-registered
         * `IScannerInsertionPointProvider`.
         */
        val INS_EXTENSION_PROVIDED: Byte = 0x41
        /**
         * Used to indicate where the payload is inserted at an unknown location
         * within the request.
         */
        val INS_UNKNOWN: Byte = 0x7f
    }
}
