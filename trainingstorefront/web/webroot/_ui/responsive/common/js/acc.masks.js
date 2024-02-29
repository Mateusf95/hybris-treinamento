ACC.masks = {

	_autoload: [
		    "init"
		],

	init: function(){
	    $('.dateMask').mask('00/00/0000', {placeholder: "--/--/----"})
	    $('.cpfMask').mask('000.000.000-00', {placeholder: "___.___.___-__"})
	},

};