import Backbone from 'backbone'
import $ from 'jquery'
const Config = require('../../main.page.config.json');

const PartnerModelConfig = Config.partnerModel;

Backbone.$ = $;


export default class PartnerModel extends Backbone.Model {

    url() {
        return PartnerModelConfig.url;
    }
}