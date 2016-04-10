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

            var template = Application.fetchTemplate(this.templateName);

            if(!template)
            {
                console.error("Template " + this.templateName + " not found");
                return;
            }

            $("[data-view=" + this.dataTag + "]").empty().append(Handlebars.compile(template)(data));
        },

        extend: function(object){
            return $.extend({}, this, object);
        }
    },

    fetchTemplate: function(templateName){
        var template = undefined;
        var url = 'templates/' + templateName + '_tpl.hbs';
        var self = this;

        $.ajax({
            url: url,
            method: 'GET',
            async: false,
            success: function(data){ template = data; },
            error: function(){ console.error("Error fetching template " + self.templateName); }
        });

        return template;
    },

    flash: function(message, level){
        var template = Application.fetchTemplate("flash");
        level = level || "warning";

        if(template){
            var $flash = function(){ return $("#flash-message"); };
            var $flashRemove = function(callback){
                $flash().animate({top: "-100px"}, {complete: function(){
                    $flash().remove();
                    if(typeof callback === "function") callback();
                }});
            };

            var $flashCreate = function(){
                $("body").prepend(Handlebars.compile(template)({message: message, level: level}));
                $flash().animate({top: "50px"});
                $flash().find("button").click(function(){ $flashRemove(); });
            };

            if($flash().length > 0){ $flashRemove($flashCreate); }
            else{ $flashCreate(); }
        }
    }
};

$(function(){
    for(var viewName in Application.views) Application.views[viewName].initialize();
    for(var viewName in Application.views) Application.views[viewName].render();
});