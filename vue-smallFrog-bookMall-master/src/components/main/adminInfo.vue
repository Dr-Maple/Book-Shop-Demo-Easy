<template>
<div>
    <div class="adminInfo">
        <el-form status-icon label-width="100px" ref="adminInfo" :model="adminInfo" label-position="right" >

            <el-form-item label="头像：" prop="profilePhoto" >
                <el-row :gutter="10" type="flex">
                  <el-col :span="12">
                      <el-avatar shape="square" :size="100" fit="fill" :src="'../../../static/profilePhoto/' + adminInfo.profilePhoto"></el-avatar>
                  </el-col>
                  <el-col :span="12">
                      <el-upload action="http://localhost:8080/uploadProfilePhoto" :on-success="changeProfilePhoto">
                        <el-button>更改</el-button>
                    </el-upload>
                  </el-col>
                </el-row>
            </el-form-item>
                <el-form-item label="昵称：" prop="username"><el-button type="text" @click="editAdminName">{{adminInfo.username}}</el-button></el-form-item>
            <el-row>
              <el-col :span="12">
                    <el-form-item>
                    <el-button type="primary" @click="modifyPasswordDialog = true">修改密码</el-button>
            </el-form-item>
              </el-col>
            </el-row>
        </el-form>
    </div>

    <el-dialog title="修改密码" :visible.sync="modifyPasswordDialog" width="30%">
        <el-form :rules="rules" :model="ruleForm" label-position="right" label-width="100px" ref="ruleForm">
            <el-form-item label="原密码：" prop="oldPassword"><el-input autocomplete="off" v-model="ruleForm.oldPassword"></el-input></el-form-item>
            <el-form-item label="新密码：" prop="newPassword"><el-input autocomplete="off" v-model="ruleForm.newPassword"></el-input></el-form-item>
            <el-form-item label="确认密码：" prop="checkPassword"><el-input autocomplete="off" v-model="ruleForm.checkPassword"></el-input></el-form-item>
            <el-form-item>
                <el-button @click="modifyPasswordDialog = false">取 消</el-button>
                <el-button type="primary" @click="modifyPassword('ruleForm')">确 定</el-button>
            </el-form-item>
        </el-form>
    </el-dialog>
</div>
</template>

<script>

import 'element-ui/lib/theme-chalk/display.css'
import axios from 'axios'
export default {
    data(){
        var validateOldPass = (rule, value, callback) => {
            if(value == ''){
                callback(new Error('请输入原始密码'))
            }else{
                if(value !== this.adminInfo.password){
                    callback(new Error('密码错误，请再次输入密码'))
                }
                callback();
            }
        };
        var validateNewPass = (rule, value, callback) => {
            if (value === '') {
                callback(new Error('请输入密码'));
            } else {
                if (this.ruleForm.checkPassword !== '') {
                    this.$refs['ruleForm'].validateField('checkPassword');
                }
                callback();
            }
        };
        var validateCheckPass = (rule, value, callback) => {
            if (value === '') {
                callback(new Error('请再次输入密码'));
            } else if (value !== this.ruleForm.newPassword) {
                    callback(new Error('两次输入密码不一致!'));
                } else {
                callback();
            }
        };

        return{
            rules:{
                oldPassword: [
                    {  required: true, validator: validateOldPass, trigger: 'blur' },
                ],
                newPassword: [
                    { required: true, validator: validateNewPass, trigger: 'blur' },
                ],
                checkPassword:[
                    { required: true, validator: validateCheckPass, trigger: 'blur' },
                ]
            },
            ruleForm:{
                oldPassword: "",
                newPassword: "",
                checkPassword: "",
            },
            modifyPasswordDialog: false,
            editStatus: true,
            adminInfo: {},
        }
    },
    created(){
        this.adminInfo =  this.$cookies.get('user')
    },
    methods:{
        modifyPassword(formName){
            this.$refs[formName].validate((valid) => {
                if (valid) {
                    alert('submit!');
                    this.adminInfo.password = this.ruleForm.newPassword;
                    this.$cookies.set('user', this.adminInfo)
                    this.updateAdmin()
                    this.modifyPasswordDialog = false
                } else {
                    console.log('error submit!!');
                    return false;
                }
            });
        },
        
        changeProfilePhoto(res, file){
            this.adminInfo.profilePhoto = file.name;
            this.$cookies.set('user', this.adminInfo)
            this.updateAdmin()
        },
        editAdminName(){
            this.$prompt('请输入你的昵称', '提示', {
                confirmButtonText: '确定',
                cancelButtonText: '取消',
                inputValue: this.adminInfo.username
                }).then(({ value }) => {
                    this.$message({
                        type: 'success',
                        message: '修改之后的昵称是: ' + value
                    });
                    this.adminInfo.username = value
                    this.$forceUpdate()
                    this.$cookies.set('user', this.adminInfo)
                    this.updateAdmin()
                }).catch(() => {
                this.$message({
                    type: 'info',
                    message: '取消输入'
                });       
            });
            
        }, 
        updateAdmin(){
            axios.post("/admin/updateAdmin", this.adminInfo).then(res=>{

            })
        },
    }
}
</script>

<style>
.adminInfo{
    width: 500px;
    margin: auto;
    margin-top: 150px;
    padding: 20px 60px 20px 40px;
    border-radius: 10px;
    background-color: #4f6e9d;
    
}
.adminInfo .el-form-item__label{
    color: white;
}
</style>