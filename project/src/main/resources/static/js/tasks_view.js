Application.views.Tasks = Application.View.extend({
    dataTag: "tasks",
    templateName: "tasks",
    
    render: function(){
		Handlebars.registerHelper('view_url', function(id){
			return "view/" + id;
		});
		
		Handlebars.registerHelper('edit_url', function(id){
			return "edit/" + id;
		});
		
		Handlebars.registerHelper('get_flash_message', function (){
			return "";
		});

        var self = this;

        $.ajax({
            url : "api/tasks-list",
            success : function(data) {
                self.template({tasks: data});
            },
            error: function(_, textError){
                self.template({tasks: []});
                Application.flash("Impossible de récupérer la liste des tâches", "danger");
                console.error("Impossible de récupérer la liste des tâches, raison: " + textError);
            }
        });
	
		// Delete function	
		$('.btn-danger').click(function(event) {
			event.preventDefault();
			var task_id = $(event.target).data("target");

			var element_name = "task-" + task_id;

			$.ajax({
				url : "api/task-delete/" + task_id,
				type : 'DELETE',
				success : function() {
					$('div[data-name="' + element_name + '"]').remove();
				},
                error: function(){
                    Application.flash("Impossible de supprimer la tâche", "danger");
                }
			});
		});
		
		// View	
		$('.btn-info').click(function(event) {
			event.preventDefault();
			var task_id = $(event.target).data("name");
			window.location.href = "view/"+ task_id;
		});
	}
});