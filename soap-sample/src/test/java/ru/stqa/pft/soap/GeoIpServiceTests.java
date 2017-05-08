package ru.stqa.pft.soap;

import net.webservicex.GeoIP;
import net.webservicex.GeoIPService;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

public class GeoIpServiceTests {

  @Test
  public void testMyIp() {
    GeoIP geoIP = new GeoIPService().getGeoIPServiceSoap12().getGeoIP("178.71.130.33");
    assertEquals(geoIP.getCountryCode(), "RUS"); // сравниваем с ожидаемым значением
  }

  @Test
  public void testInvalidIp() {
    GeoIP geoIP = new GeoIPService().getGeoIPServiceSoap12().getGeoIP("178.71.130.xxx");
    assertEquals(geoIP.getCountryCode(), "RUS"); // сравниваем с ожидаемым значением
  }
}
