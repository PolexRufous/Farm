import PartnersView from './views/PartnersView'
import PartnersCollection from './collections/PartnersCollection'
import Backbone from 'backbone'

const PartnersCollectionCreated = new PartnersCollection();

const Application = {
    Collections : {
        "partners" : PartnersCollectionCreated
    },
    Views : {
        "partners" : new PartnersView({model : PartnersCollectionCreated})
    },
    Routes : {},

    run : function () {
        this.Views.partners.refresh();
        Backbone.history.start();

    }
};

Application.run();

exports.application = Application;