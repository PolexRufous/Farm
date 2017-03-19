import Backbone from 'backbone'
import $ from 'jquery'
import dust from 'dustjs-linkedin'
import PartnerRowView from './PartnerRowView'
import PartnerModel from '../models/PartnerModel'

const partnersTableTemplate = require('../../templates/partners.table.dust');
const Config = require('../../main.page.config.json');
const PartnersViewConfig = Config.partnersView;
Backbone.$ = $;

export default class PartnersView extends Backbone.View {
    get el() {
        return '#' + PartnersViewConfig.el;
    }

    get table() {
        return this.$('#' + PartnersViewConfig.tableId);
    }

    constructor(options)
    {
        super(options);
    }

    render() {
        let self = this;
        dust.render(partnersTableTemplate, PartnersViewConfig, function (err, result) {
            self.$el.html(result);
            $.each(self.model.models, function (i, model) {
                self.table.append(new PartnerRowView({model: model}).render());
            });
        });
    }

    refresh() {
        let self = this;
        this.model.fetch({
            success: function () {
                self.render();
            }
        })
            .fail(function () {
                console.error('Error fetching partners!')
            });
    }

    addPartner() {
        let partnerModel = new PartnerModel();
        this.model.add(partnerModel);
        this.table.append(new PartnerRowView({model: partnerModel}).render());
    }

    get events()
    {
        return {
            'click .refresh-button': 'refresh',
            'click .add-partner-button': 'addPartner'
        }
    }
}