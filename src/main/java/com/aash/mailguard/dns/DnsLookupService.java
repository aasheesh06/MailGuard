package com.aash.mailguard.dns;

import com.aash.mailguard.exception.DnsLookupException;
import org.springframework.stereotype.Component;
import org.xbill.DNS.*;
import org.xbill.DNS.Record;

@Component
public class DnsLookupService {
    public boolean hasMxRecord(String domain){

        try {
            Lookup lookup=new Lookup(domain, Type.MX);

            Record[] records= lookup.run();

            if (records != null && records.length>0) {
                return true;
            }

            return false;
        } catch (TextParseException e) {
            throw new DnsLookupException("Unable to perform DNS lookup.", e);
        }
    }

    public String getMxHost(String domain){
        try {
            Lookup lookup = new Lookup(domain, Type.MX);

            Record[] records = lookup.run();

            if (records == null || records.length == 0) {
                return null;
            }

            MXRecord mxRecord = (MXRecord) records[0];

            String host = mxRecord.getTarget().toString();

            if (host.endsWith(".")) {
                host = host.substring(0, host.length() - 1);
            }

            return host;

        } catch (TextParseException e) {
            throw new DnsLookupException("Unable to perform DNS lookup.", e);
        }
    }
}
