(function() {
	window.GATE = (window.GATE || {});
	window.GATE.Helper = (window.GATE.Helper || {});
	
    var accentMap = {
      "á": "a",
      "ö": "o",
	  	"ä": "a",
	  	"ü": "u"
    };
	
	window.GATE.Helper.Initialize = function(){
		
			$("GD-Item-FunctionScoreName").autocomplete({
			source: function( request, response ) {
				var matcher = new RegExp( $.ui.autocomplete.escapeRegex( request.term ), "i" );
				response( $.grep( fsNames, function( value ) {
				value = value.label || value.value || value;
				return matcher.test( value ) || matcher.test( window.GATE.Helper.normalize( value ) );
				}) );
			}
			});
	}
	
  window.GATE.Helper.normalize = function( term ) {
      var ret = "";
      for ( var i = 0; i < term.length; i++ ) {
        ret += accentMap[ term.charAt(i) ] || term.charAt(i);
      }
      return ret;
    };
	
	window.GATE.Helper.attachAutoComplition = function(request, response , values){
        var matcher = new RegExp( $.ui.autocomplete.escapeRegex( request.term ), "i" );
        response( $.grep( values, function( value ) {
          value = value.label || value.value || value;
          return matcher.test( value ) || matcher.test( window.GATE.Helper.normalize( value ) );
        }));
  }
	

})(jQuery);

$(document).ready(function() {
	window.GATE.Helper.Initialize();
	
});