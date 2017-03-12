'use strict';

//css
import '../../global/css/default.clean.css'
import '../css/layout.css'

import dust from 'dustjs-linkedin'
import $ from 'jquery'

const header = require('../../global/dusttemplates/header.dust');
const main = require('../../global/dusttemplates/main.dust');
const footer = require('../../global/dusttemplates/footer.dust');
const Config = require('../main.page.config.json');


const buildMainPage = function () {

    dust.render(header, {}, function (err, result) {
        $('header').html(result);
    });

    dust.render(main, {}, function (err, result) {
        $('main').html(result);
    });

    dust.render(footer, {}, function (err, result) {
        $('footer').html(result);
    });

    $('.right-side').attr('id', Config.partnersView.el);
};

buildMainPage();
exports.buildMainPage = buildMainPage;
