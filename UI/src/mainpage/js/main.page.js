import PartnersView from './views/PartnersView'
import PartnersCollection from './collections/PartnersCollection'

const Application = {
    Collections : {},
    Views : {
        "partners" : new PartnersView({model : new PartnersCollection()})
    },
    Routes : {}
};

exports.application = Application;