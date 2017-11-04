function findOfficials(name, country) {
    $('#result').load(encodeURI('/search/official?name='+name+'&countryName='+country.trim()));
    return false;
}

function suggest(name, country) {
    var result = null;
    $.ajax({url: encodeURI('/api/suggest?name=' + name + '&countryName=' + country.trim()),
        data: {name: name, countryName: country},
    type: 'get', success: function(data) {
            return data;
    }});
}

function suggestURL(name, country) {
    return encodeURI('/api/suggest?name=' + name + '&countryName=' + country.trim());
}