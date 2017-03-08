'use strict';

//css
import clean_css from '../../global/css/default.clean.css'
import layout_css from '../css/layout.css'

import dust from '../../../node_modules/dustjs-linkedin/dist/dust-core.min'
import $ from '../../../node_modules/jquery/dist/jquery.min'

var header = require('../../global/dusttemplates/header.dust');
var main = require('../../global/dusttemplates/main.dust');
var footer = require('../../global/dusttemplates/footer.dust');


var buildMainPage = function () {

    dust.render(header, {}, function (err, result) {
        $('header').html(result);
    });

    dust.render(main, {}, function (err, result) {
        $('main').html(result);
    });

    dust.render(footer, {}, function (err, result) {
        $('footer').html(result);
    });
}

exports.pageBuilder = buildMainPage;
