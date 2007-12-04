<!-- ******************* Add forEach to JavaScript ******************* -->
if (!Array.prototype.forEach)
{
    Array.prototype.forEach = function(fun /*, thisp*/)
    {
        var len = this.length;
        if (typeof fun != "function")
            throw new TypeError();

        var thisp = arguments[1];
        for (var i = 0; i < len; i++)
        {
            if (i in this)
                fun.call(thisp, this[i], i, this);
        }
    };
}

<!-- ************************* Try.allThese ************************** -->
Try.allThese = function() {
    var returnValue = [];
    for (var i = 0; i < arguments.length; i++) {
        var lambda = arguments[i];
        try {
            returnValue.push(lambda());
        } catch (e) {
            e = null;
            returnValue.push(false);
        }
    }
    return returnValue;
};

<!-- ****************** ShareWI Map and Geocode Script**************** -->
var map;
var geocoder;
var address;
var choices;
var choicesMessage;

function buildAddressLink(element, index, array) {
    var addressLink = Builder.node('li', {}, [
            Builder.node('a', {href:"javascript:void(0)",
                onclick:"fetchGeocodeMap('" + element.address + "');return false;"}, element.address) ]);
    choices.appendChild(addressLink);
    return false;
}

// getFormAddress() creates a well-formatted
// address string from multiple fields of the form
function getFormAddress() {
    address = $F("locationForm:street") + ", " +
              $F("locationForm:city") + ", " +
              $F("locationForm:province") + " " +
              $F("locationForm:postalCode");
    if (undefined != $("locationForm:country")) address += ", " + $F("locationForm:country");
    return address;
}

// setFormAddress(place) fills out the form
// with returned address strings
function setFormAddress(place) {
    if (place) {
        //Fillout Hidden Lats/Longs
        Try.allThese(
                function() {
                    $("locationForm:lat").value = place.Point.coordinates[1]
                },
                function() {
                    $("locationForm:long").value = place.Point.coordinates[0]
                });

        //Fillout the rest of the address form
        Try.allThese(
                function() {
                    $("locationForm:street").value = place.AddressDetails.Country
                            .AdministrativeArea.SubAdministrativeArea
                            .Locality.Thoroughfare.ThoroughfareName;
                },
                function() {
                    $("locationForm:province").value = place.AddressDetails.Country
                            .AdministrativeArea.AdministrativeAreaName;
                },
                function() {
                    $("locationForm:country").value = place.AddressDetails.Country.
                            CountryNameCode;
                });
        Try.these(
                function() {
                    $("locationForm:city").value = place.AddressDetails.Country
                            .AdministrativeArea.SubAdministrativeArea
                            .Locality.LocalityName;
                },
                function() {
                    $("locationForm:city").value = place.AddressDetails.Country
                            .AdministrativeArea
                            .Locality.LocalityName;
                });
        Try.these(
                function() {
                    $("locationForm:postalCode").value = place.AddressDetails.Country
                            .AdministrativeArea.SubAdministrativeArea
                            .Locality.PostalCode.PostalCodeNumber;
                },
                function() {
                    $("locationForm:postalCode").value = place.AddressDetails.Country
                            .AdministrativeArea
                            .Locality.PostalCode.PostalCodeNumber;
                });
    }
    return false;
}

// addAddressToMap() is called when the geocoder returns an
// answer.  It adds a marker to the map with an open info window
// showing the nicely formatted version of the address and the country code.
function addAddressToMap(response) {
    map.clearOverlays();
    if (!response || response.Status.code != 200) {
        alert("Sorry, we were unable to geocode that address.  Code: " + response.Status.code);
    } else {
        if (response.Placemark.length == 1) {
            place = response.Placemark[0];
            point = new GLatLng(place.Point.coordinates[1], place.Point.coordinates[0]);
            marker = new GMarker(point);
            map.addOverlay(marker);
            marker.openInfoWindowHtml(place.address);

            setFormAddress(place);
        }
        else {
            choicesMessage.show();
            response.Placemark.forEach(buildAddressLink);
        }
    }
    return false;
}

// showLocation() is called when you click on the Search button
// in the form.  It geocodes the address entered into the form
// and adds a marker to the map at that location.
function fetchGeocodeMap(address) {
    choicesMessage.hide();
    while (choices.hasChildNodes()) choices.removeChild(choices.firstChild);

    if (geocoder) {
        if (address) geocoder.getLocations(address, addAddressToMap);
        else geocoder.getLocations(getFormAddress(), addAddressToMap);
    }
    return false;
}

function initialize() {
    if (GBrowserIsCompatible()) {
        map = new GMap2($("locationForm:GMap"));
        map.addControl(new GSmallMapControl());
        map.addControl(new GMapTypeControl());

        map.setCenter(new GLatLng(41.882193, -87.624465), 13);
        geocoder = new GClientGeocoder();

        choices = $("locationForm:GPlacemarks");
        choicesMessage = $("locationForm:choicesMessage");
        choicesMessage.hide();
    }
    return false;
}