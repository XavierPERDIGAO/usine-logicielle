Application.views.Test = Application.View.extend({
    dataTag: "test",
    templateName: "main",
    
    render: function(){
        this.template({text: "lol"});
    }
});