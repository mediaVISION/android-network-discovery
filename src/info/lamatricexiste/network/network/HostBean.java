/*
 * Copyright (C) 2009-2010 Aubort Jean-Baptiste (Rorist)
 * Licensed under GNU's GPL 2, see README
 */

// Inspired by http://connectbot.googlecode.com/svn/trunk/connectbot/src/org/connectbot/bean/HostBean.java
package info.lamatricexiste.network.network;

import info.lamatricexiste.network.R;
import info.lamatricexiste.network.activities.MainActivity;

import java.util.ArrayList;
import java.util.HashMap;

import android.os.Parcel;
import android.os.Parcelable;

public class HostBean implements Parcelable {

    public static final String EXTRA = MainActivity.PKG + ".extra";
    public static final String EXTRA_POSITION = MainActivity.PKG + ".extra_position";
    public static final String EXTRA_HOST = MainActivity.PKG + ".extra_host";
    public static final String EXTRA_TIMEOUT = MainActivity.PKG + ".network.extra_timeout";
    public static final String EXTRA_HOSTNAME = MainActivity.PKG + ".extra_hostname";
    public static final String EXTRA_BANNERS = MainActivity.PKG + ".extra_banners";
    public static final String EXTRA_PORTSO = MainActivity.PKG + ".extra_ports_o";
    public static final String EXTRA_PORTSC = MainActivity.PKG + ".extra_ports_c";
    public static final String EXTRA_SERVICES = MainActivity.PKG + ".extra_services";
    public static final String EXTRA_USER_GIVEN_NAME = MainActivity.PKG + ".extra_host_user_given_name";
    public static final String EXTRA_ICON = MainActivity.PKG + ".extra_host_icon";
    public static final int TYPE_GATEWAY = 0;
    public static final int TYPE_COMPUTER = 1;

    public int deviceType = TYPE_COMPUTER;
    public int isAlive = 1;
    public int position = 0;
    public int responseTime = 0; // ms
    public String ipAddress = null;
    public String hostname = null;
    private String hardwareAddress = NetInfo.NOMAC;
    public String nicVendor = "Unknown";
    public String userGivenName = "";
    public String os = "Unknown";
    public boolean isThisThisDevice = false;
    public int icon = R.drawable.ic_network_device_network_lan;
    public HashMap<Integer, String> services = null;
    public HashMap<Integer, String> banners = null;
    public ArrayList<Integer> portsOpen = null;
    public ArrayList<Integer> portsClosed = null;

    public HostBean() {
        // New object
    }

    public HostBean(Parcel in) {
        // Object from parcel
        readFromParcel(in);
    }

    public String getHardwareAddress() {
		return hardwareAddress;
	}

	public void setHardwareAddress(String hardwareAddress) {
		
		if(hardwareAddress == null || hardwareAddress.length() < 1){
			this.hardwareAddress = NetInfo.NOMAC;
		} else {
			this.hardwareAddress = hardwareAddress.toUpperCase();
		}
	}

	@SuppressWarnings("rawtypes")
	public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public HostBean createFromParcel(Parcel in) {
            return new HostBean(in);
        }

        public HostBean[] newArray(int size) {
            return new HostBean[size];
        }
    };

    public int describeContents() {
        return 0;
    }

    @SuppressWarnings("unchecked")
    private void readFromParcel(Parcel in) {
        deviceType = in.readInt();
        isAlive = in.readInt();
        ipAddress = in.readString();
        hostname = in.readString();
        hardwareAddress = in.readString();
        nicVendor = in.readString();
        os = in.readString();
        responseTime = in.readInt();
        position = in.readInt();
        services = in.readHashMap(null);
        banners = in.readHashMap(null);
        portsOpen = in.readArrayList(Integer.class.getClassLoader());
        portsClosed = in.readArrayList(Integer.class.getClassLoader());
        userGivenName = in.readString();
        icon = in.readInt();
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(deviceType);
        dest.writeInt(isAlive);
        dest.writeString(ipAddress);
        dest.writeString(hostname);
        dest.writeString(hardwareAddress);
        dest.writeString(nicVendor);
        dest.writeString(os);
        dest.writeInt(responseTime);
        dest.writeInt(position);
        dest.writeMap(services);
        dest.writeMap(banners);
        dest.writeList(portsOpen);
        dest.writeList(portsClosed);
        dest.writeString(userGivenName);
        dest.writeInt(icon);
    }
}
