import Backbone from 'backbone'
import $ from 'jquery'
import PartnerModel from '../models/PartnerModel'
const Config = require('../../main.page.config.json');

const PartnersCollectionConfig = Config.partnersCollection;

Backbone.$ = $;

export default class PartnersCollection extends Backbone.Collection{
    get model(){
        return PartnerModel;
    }
    url(){
        return PartnersCollectionConfig.url;
    }
}