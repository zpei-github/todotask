<template>
  <div>
    <el-button type="primary" @click="showTaskDialog = true">添加事项</el-button>
    <el-dialog title="事项详情" :visible.sync="showTaskDialog" @close="resetCurrentTask">
      <el-form :model="currentTask">
        <el-form-item label="事项内容">
          <el-input v-model="currentTask.content" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="预定完成时间">
          <el-date-picker v-model="currentTask.completedAt" type="datetime" placeholder="选择日期时间"></el-date-picker>
        </el-form-item>
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button @click="showTaskDialog = false">取消</el-button>
        <el-button type="primary" @click="handleAddTask">确认</el-button>
      </span>
    </el-dialog>

    <el-dialog title="事项详情" :visible.sync="editDialog" @close="resetCurrentTask">
      <el-form :model="currentTask">
        <el-form-item label="事项内容">
          <el-input v-model="currentTask.content" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="预定完成时间">
          <el-date-picker v-model="currentTask.completedAt" type="datetime" placeholder="选择日期时间"></el-date-picker>
        </el-form-item>
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button @click="editDialog = false">取消</el-button>
        <el-button type="primary" @click="editPost(currentTask)">确认</el-button>
      </span>
    </el-dialog>


    <el-table :data="tasks" style="width: 100%" v-loading="isLoading">
      <el-table-column label="事项内容" width="180">
        <template #default="scope">
          <div :class="{ 'completed-task': scope.row.isCompleted }">
            {{ scope.row.content }}
          </div>
        </template>
      </el-table-column>
      <el-table-column label="预计完成时间" width="180">
        <template #default="scope">
          <div>
            {{formatDate(scope.row.completedAt)}}
          </div>
        </template>
      </el-table-column>
      <el-table-column label="完成状态" width="120">
        <template #default="scope">
          <el-checkbox v-model="scope.row.isCompleted" @change="() => toggleTaskStatus(scope.row)"></el-checkbox>
        </template>
      </el-table-column>
      <el-table-column label="操作" width="180">
        <template #default="scope">
          <el-button size="mini" @click="() => editTask(scope.row)">编辑</el-button>
          <el-button size="mini" type="danger" @click="() => deleteTask(scope.row.taskId)">删除</el-button>
        </template>
      </el-table-column>
    </el-table>
  </div>
</template>

<script>
import http from '@/utils/http';

export default {
  data() {
    return {
      showTaskDialog: false,
      editDialog : false,
      tasks: [],
      isLoading: false,
      currentTask: {taskId: null, content: '', isCompleted: false, completedAt: ''},
    };
  },
  methods: {
    async fetchTasks() {
      try {
        this.isLoading = true;
        // 在请求中使用配置
        const response = await http.get('/task/getTasks');
        this.tasks = response.data;

        console.log(this.tasks);
      } catch (error) {
        this.$message.error('获取任务失败');
        console.error('Error fetching tasks:', error);
      } finally {
        this.isLoading = false;
      }
    },

    async handleAddTask() {
      try {
        // 使用配置发送POST请求
        await http.post('/task/addTask', this.currentTask);
        this.showTaskDialog = false;
        await this.fetchTasks();
      } catch (error) {
        this.$message.error('添加任务失败');
        console.error('Error adding task:', error);
      }
    },

    async editPost() {
      try {
        await http.put(`/task/editTask/${this.currentTask.taskId}`, {content: this.currentTask.content,
          completedAt: this.currentTask.completedAt,
          isCompleted: this.currentTask.isCompleted
        });
        this.editDialog = false;
        await this.fetchTasks();
      } catch (error) {
        this.$message.error('更新任务失败');
        console.error('Error updating task:', error);
      }
    },
    async editTask(task) {
      this.currentTask = task;
      console.log("EditingTask",this.currentTask);
      this.editDialog = true;
    },
    async deleteTask(taskId) {
      try {
        await http.delete(`/task/deleteTask/${taskId}`);
        await this.fetchTasks();
      } catch (error) {
        this.$message.error('删除任务失败');
        console.error('Error deleting task:', error);
      }
    },
    async toggleTaskStatus(task) {
      console.log("获取的值",task.isCompleted);
      let mark = !task.isCompleted;
      console.log(mark);
      try {
        http.patch(`/task/patchTask/${task.taskId}`, {content: task.content, completedAt: Date.now(), isCompleted: mark});
        task.isCompleted = mark;
      } catch (error) {
        this.$message.error('更改任务状态失败');
        console.error('Error toggling task status:', error);
      }
    },
    resetCurrentTask() {
      this.currentTask = {taskId: null, content: '', isCompleted: false, completedAt: ''};
    },
    formatDate(date) {
      const d = new Date(date);
      let month = '' + (d.getMonth() + 1),
          day = '' + d.getDate(),
          year = d.getFullYear();

      if (month.length < 2)
        month = '0' + month;
      if (day.length < 2)
        day = '0' + day;

      return [year, month, day].join('-');
    }
  },
  mounted() {
    this.fetchTasks();
  }
};
</script>

<style>
.completed-task {
  color: grey;
  text-decoration: line-through;
}
</style>
