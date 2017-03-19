import Backbone from 'backbone'
import $ from 'jquery'
import dust from 'dustjs-linkedin'

const partnerRowTemplate = require('../../templates/partner.row.dust');
const partnerRowEditTemplate = require('../../templates/partner.row.edit.dust');
const Config = require('../../main.page.config.json');
const PartnerConfig = Config.partnerView;
Backbone.$ = $;

export default class PartnerRowView extends Backbone.View{

    get tagName() {
        return PartnerConfig.tagName;
    }

    get events()
    {
        return {
            'click .partner-save-button': 'save',
            'click .partner-cancel-button': 'cancelEdit',
            'click .partner-update-button': 'renderEditableRow'
        }
    }

    get modelParams() {
        return {
            name: this.$("input[name='name']").val(),
            description: this.$("input[name='description']").val()
        }
    }

    render() {
        if (this.model.isNew()) {
            this.renderEditableRow();
        } else {
            this.renderSimpleRow();
        }
        return this.$el;
    }

    renderSimpleRow() {
        let self = this;
        let renderParams = $.extend(PartnerConfig, this.model.toJSON());
        dust.render(partnerRowTemplate, renderParams, function (err, result) {
            $(self.el).html(result);
        });

    }

    renderEditableRow() {
        let self = this;
        let renderParams = $.extend(PartnerConfig, this.model.toJSON());
        dust.render(partnerRowEditTemplate, renderParams, function (err, result) {
            $(self.el).html(result);
        });
    }

    save() {
        let self = this;
        this.model.save(this.modelParams, {
            success: function () {
                self.render();
            }
        })
                .fail(function () {
                    console.error('Fail when save partner!');
                });
    }

    cancelEdit() {
        if(this.model.isNew()) {
            this.model.destroy();
            this.remove();
        } else {
            this.render();
        }
    }
}
