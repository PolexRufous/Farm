import $ from '../../../node_modules/jquery/dist/jquery.min'

var greenFooter = function () {
    $('header').css('background-color', 'green');
};

exports.greenFooter = greenFooter;