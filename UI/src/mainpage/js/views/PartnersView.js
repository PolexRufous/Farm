import Backbone from 'backbone'
import $ from 'jquery'
import dust from 'dustjs-linkedin'

const partnersTableTemplate = require('../../templates/partners.table.dust');
const Config = require('../../main.page.config.json');
const PartnersViewConfig = Config.partnersView;
Backbone.$ = $;

export default class PartnersView extends Backbone.View{
    get el() {
        return '#' + PartnersViewConfig.el;
    }

    render() {
        let self = this;
        dust.render(partnersTableTemplate, PartnersViewConfig, function (err, result) {
            $(self.el).html(result);
        });
    }
}