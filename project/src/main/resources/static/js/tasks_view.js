Application.views.Tasks = Application.View.extend({
    dataTag: "tasks",
    templateName: "tasks",
    
    render: function(){
    	
    	/** Helper to return the show url corresponding to the ID */
		Handlebars.registerHelper('show_url', function(id){
			return "show?id=" + id;
		});
		
		/** Helper to return the edit url corresponding to the ID */
		Handlebars.registerHelper('edit_url', function(id){
			return "edit?id=" + id;
		});

		/** Store the context to be able to use it later */
    	var self = this;
        
    	/** Ajax call to the API to recover the list of all the tasks */
        $.ajax({
            url : "api/tasks-list",
            success : function(data) {
                self.template({tasks: data});
                
        		/** On click on all the delete buttons */
        		$('.btn-danger').click(function(event) {

        			event.preventDefault();

        			/** Ask confirmation to user */
        			if (confirm("Delete this task ? ")) {
        			
        			/** Recover the task id */
        			var task_id = $(event.target).data("target");

        			/** Ajax call to the API to delete the task */
        			$.ajax({
        				url : "api/task-delete/" + task_id,
        				type : 'DELETE',
        				success : function() {
        					
        					/** User feedback and removal of the task */
        					Application.flash("Tache supprimée avec succès");
        					$('div[data-name="' + task_id + '"]').remove();
        				},
                        error: function(){
                        	
                        	/** User feedback */
                            Application.flash("Impossible de supprimer la tâche", "danger");
                        }
        			});
        			}
        		});
        		
            },
            error: function(_, textError){
                self.template({tasks: []});
                Application.flash("Impossible de récupérer la liste des tâches", "danger");
            }
        });
	}
});