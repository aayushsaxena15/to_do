Vue.component('task-row', {
  template: '<tr :class="{ completed: task.completed }">
              <td class="task" v-on:click="completeTask">
                <span class="task-text">{{ task.text }}</span><br>
                <span class="date">Created: {{ task.created }}</span>
                <span v-if="task.completed" class="date">| Completed: {{ task.completeDate }}</span>
              </td>
              <td>
                <div v-if="task.completed" class="btn-group-sm pull-right" role="group">
                <button type="button" class="btn btn-default" v-on:click="deleteTask"><span class="glyphicon glyphicon-remove" aria-hidden="true"></span></button>
                </div>
                <div v-else class="btn-group btn-group-sm pull-right" role="group">
                <button type="button" class="btn btn-default" v-on:click="completeTask"><span class="glyphicon glyphicon-ok" aria-hidden="true"></span></button>
                <button type="button" class="btn btn-default" v-on:click="deleteTask"><span class="glyphicon glyphicon-remove" aria-hidden="true"></span></button>
                </div>
              </td>
              </tr>'
  props: ['index', 'task']
  methods: {
    completeTask: () ->
      this.$emit('complete', this.task)
    deleteTask: () ->
      this.$emit('deltask', this.task)
  }
})

new Vue({
  el: '#wrapper'
  data: {
    page: "todo"
    tasks: []
    task: ""
    filterTask: ""
  }
  methods: {
    navigate: (e, page) ->
      $(e.target).parent().parent().find("li.active").removeClass("active")
      $(e.target).parent().addClass("active")
      this.page = page
    addTask: () ->
      data = new Object()
      data.text = this.task
      self = this
      this.$http.post('/task', data).then(
        (response) ->
          self.tasks.unshift(response.body)
      )
      this.task = ""
    completeTask: (task) ->
      if(!task.completed)
        index = this.tasks.indexOf(task)
        self = this
        this.$http.post('/task/complete/'+task.id, "{}").then(
          (response) ->
            self.tasks.splice(index, 1, response.body)
          (errorResponse) ->
            console.log(errorResponse)
        )
    deleteTask: (task) ->
      this.$http.delete('/task/'+task.id)
      this.tasks.splice(this.tasks.indexOf(task), 1)
  }
  computed: {
    showTasks: () ->
      data = this.tasks

      if(this.page == "todo")
        data = this.tasks.filter((task) ->
          task.completed == false
        )
      else if(this.page == "completed")
        data = this.tasks.filter((task) ->
          task.completed == true
        )

      if(this.filterTask.length == 0)
        return data

      filter = this.filterTask.toUpperCase()
      return data.filter((task) ->
        task.text.toUpperCase().match(filter)
      )
  }
  mounted: () ->
    this.$http.get('/task').then(
      (response) ->
        this.tasks = response.body
    )
})
