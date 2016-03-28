/**
 * Point d'entrée de la logique de l'application.
 */
var Application = {
    /**
     * Ensemble des vues de l'application qui seront
     * initialisées et rendues au chargement de la page.
     */
    views: {},

    /**
     * Objet représentant une vue dans la page.
     * Cet objet est fait pour être hérité par le biais de la fonction {#extends}.
     *
     * {#templateName} représente le nom du template à rendre sans l'extension.
     * Le nom des fichiers de template doivent se terminer par "_tpl.hbs".
     * 
     * {#dataTag} permet de sélectionner la balise dans le HTML où sera rendu le template.
     * 
     */
    View: {
        templateName: undefined,
        dataTag: undefined,
        initialize: $.noop,
        render: $.noop,

        template: function(data){
            if(!this.templateName)
            {
                console.warn("No template name specified!");
                return;
            }

            if(!this.dataTag)
            {
                console.warn("No data tag specified!");
                return;
            }

            var template;
            var url = '/templates/' + this.templateName + '_tpl.hbs';
            var self = this;

            $.ajax({
                url: url,
                method: 'GET',
                async: false,
                success: function(data){ template = data; },
                error: function(){ console.error("Error fetching template " + self.templateName); }
            });

            if(!template)
            {
                console.error("Template " + this.templateName + " not found");
                return;
            }

            $("[data-view=" + this.dataTag + "]").empty().append(Handlebars.compile(template)(data));
        },

        extend: function(object){
            return $.extend(this, object);
        }
    }
};

$(function(){
    for(var viewName in Application.views) Application.views[viewName].initialize();
    for(var viewName in Application.views) Application.views[viewName].render();
});