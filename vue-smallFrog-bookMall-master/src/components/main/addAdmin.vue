<template>
    <div class="addAdminDiv">
        <el-form :model="addAdmin" :rules="rules" label-position="right" label-width="100px" ref="addAdmin">
          <el-form-item label="账号：" prop="id">
              <el-input v-model="addAdmin.id" placeholder="请输入账号" autocomplete="off"></el-input>
          </el-form-item>
          <el-form-item label="昵称：" prop="username">
              <el-input v-model="addAdmin.username" placeholder="请输入昵称" autocomplete="off"></el-input>
          </el-form-item>
          <el-form-item label="密码：" prop="newPassword">
              <el-input type="password" v-model="addAdmin.newPassword" placeholder="请输入新密码" autocomplete="off"></el-input>
          </el-form-item>
          <el-form-item label="确认密码" prop="checkPassword">
              <el-input type="password" v-model="addAdmin.checkPassword" placeholder="请再次输入密码" autocomplete="off"></el-input>
          </el-form-item>
          <el-form-item>
              <el-button type="primary" @click="submitForm('addAdmin')">确认</el-button>
              <el-button @click="resetForm('addAdmin')">重置</el-button>
          </el-form-item>
        </el-form>
    </div>
</template>

<script>
import 'element-ui/lib/theme-chalk/display.css'
import axios from 'axios'
export default {
    data(){
        var validateId = (rule, value, callback)=>{
            if(value === ""){
                callback(new Error("请输入账号"));
            }else{
                callback()
            }
        };
        var validateUsername = (rule, value, callback)=>{
            if(value === ""){
                callback(new Error("请输入昵称"));
            }else{
                callback()
            }
        };
        var validateNewPass = (rule, value, callback) => {
            if (value === "") {
                callback(new Error('请输入密码'));
            } else {
                callback();
            }
        };
        var validateCheckPass = (rule, value, callback) => {
            if (value === "") {
                callback(new Error('请再次输入密码'));
            } else if (value !== this.addAdmin.newPassword) {
                console.log( value)
                    callback(new Error('两次输入密码不一致!'));
                } else {
                callback();
            }
        };
        return{
            rules:{
                id: [
                    {required: true, validator: validateId, trigger: 'blur'}
                ],
                username: [
                    {required: true, validator: validateUsername, trigger: 'blur'}
                ],
                newPassword: [
                    { required: true, validator: validateNewPass, trigger: 'blur' },
                ],
                checkPassword:[
                    { required: true, validator: validateCheckPass, trigger: 'blur' },
                ]
            },
            addAdmin:{
                id:"",
                username:"",
                newPassword:"",
                checkPassword:"",
            },
        }
    },
    methods:{
        submitForm(formName) {
            this.$refs[formName].validate((valid) => { 
                if (valid) {
                    this.addAdminInfo();
                } else {
                    console.log('error submit!!');
                    return false;
                }
            });
        },
        addAdminInfo(){
            axios.post("/admin/addAdminInfo",{
                id : this.addAdmin.id,
                username : this.addAdmin.username,
                password : this.addAdmin.newPassword,
            }).then(res=>{
                console.log("添加成功") 
                this.$message({
                    message: '添加成功',
                    type: 'success'
                });
                this.addAdmin.id = "";
                this.addAdmin.username = "";
                this.addAdmin.newPassword = "";
                this.addAdmin.checkPassword = "";
                this.$forceUpdate();
            })
        },
        resetForm(formName) {
            this.$refs[formName].resetFields();
        }
    }
}
</script>
<style>
.addAdminDiv{
    width: 500px;
    margin: auto;
    margin-top: 150px;
    padding: 30px 60px 20px 40px;
    border-radius: 10px;
    background-color: #4f6e9d;
}
.addAdminDiv .el-form-item__label{
    color: white;
}
</style>