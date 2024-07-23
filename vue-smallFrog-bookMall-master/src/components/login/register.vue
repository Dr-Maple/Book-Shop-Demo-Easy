<template>
    <div>
        <el-row>
            <el-col :span="12">
                <img class="regImg" src="../../../static/left.jpg">
            </el-col>

            <el-col :span="12">
                <div class="title"><img class="head" src="../../../static/head2.gif"></div>
                <el-form :model="ruleForm" status-icon :rules="rules" ref="ruleForm" label-width="0" class="demo-ruleForm" label-position="right">
                    <div class="myInput id">
                        <el-form-item prop="id" label="账号：" label-width="100px">
                            <el-input class="search1 search2 search3" prefix-icon="el-icon-user" v-model="ruleForm.id" placeholder="请输入账号"></el-input>
                        </el-form-item>
                    </div>
                    <div class="myInput username" >
                        <el-form-item prop="username" label="昵称：" label-width="100px">
                            <el-input class="search1 search2 search3" prefix-icon="el-icon-user" v-model="ruleForm.username" placeholder="请输入昵称"></el-input>
                        </el-form-item>
                    </div>
                    <div class="myInput password">
                      <el-form-item label="性别：" label-width="100px" prop="sex">
                        <el-radio v-model="ruleForm.sex" label="man">男</el-radio>
                        <el-radio v-model="ruleForm.sex" label="woman">女</el-radio>
                      </el-form-item>
                    </div>
                    <div class="myInput password">
                      <el-form-item label="出生日期：" label-width="100px" prop="birthday">
                        <el-date-picker v-model="ruleForm.birthday" type="date" placeholder="选择日期" size="small" format="yyyy-MM-dd" value-format="yyyy-MM-dd"> </el-date-picker>
                      </el-form-item>
                    </div>
                    <div class="myInput password">
                        <el-form-item prop="password" label="密码：" label-width="100px">
                            <el-input class="search1 search2 search3" prefix-icon="el-icon-magic-stick" placeholder="请输入密码" v-model="ruleForm.password" show-password></el-input>
                        </el-form-item>
                    </div>
                    <div class="myInput password">
                        <el-form-item prop="checkPass" label="确认密码：" label-width="100px">
                            <el-input class="search1 search2 search3" prefix-icon="el-icon-magic-stick" placeholder="确认密码" v-model="ruleForm.checkPass" show-password></el-input>
                        </el-form-item>
                    </div>

                    <div class="login">
                        <el-form-item label-width="100px">
                            <el-button class="logBtn" @click="submitForm('ruleForm')">注册</el-button>
                        </el-form-item>
                    </div>
                </el-form>
                <div class="link">
                    <el-link type="info" @click="toLog()">已有账号?去登录!</el-link>
                </div>
            </el-col>
        </el-row>
    </div>
</template>

<script>
import axios from 'axios'

export default {
  data () {
    // <!--验证手机号是否合法-->
    let checkTel = (rule, value, callback) => {
      if (value === '') {
        callback(new Error(' '))
      } else if (!this.checkMobile(value)) {
        callback(new Error(' '))
      } else {
        callback() 
      }
    }
    // <!--验证密码-->
    let validatePass1 = (rule, value, callback) => {
      if (value === '') {
        callback(new Error(' '))
      } else {
        if (this.ruleForm.checkPass !== '') {
          this.$refs.ruleForm.validateField('checkPass')
        }
        callback()
      }
    }
    // <!--二次验证密码-->
    let validatePass2 = (rule, value, callback) => {
      if (value === '') {
        callback(new Error(' '))
      } else if (value !== this.ruleForm.password) {
        callback(new Error(' '))
      } else {
        callback()
      }
    }
    let checkBirthday = (rule, value, callback) =>{
      var date = new Date()
      if(value >= date){
          callback(new Error('请输入正确的日期'))
      }else{
          callback();
      }
    }
    return {
      ruleForm: {
        password: '',
        checkPass: '',
        id: '',
        username:"",
        sex:"",
        birthday:"",
      },
      rules: {
        password: [{
          validator: validatePass1,
          trigger: 'change'
        }],
        checkPass: [{
          validator: validatePass2,
          trigger: 'change'
        }],
        birthday:[{
          validator: checkBirthday,
          trigger: 'change'
        }]
      }
    }
  },
  methods: {
    // 验证手机号
    checkMobile (str) {
      let re = /^1\d{10}$/
      if (re.test(str)) {
        return true
      } else {
        return false
      }
    },
    // <!--提交注册-->
    submitForm (formName) {
      this.$refs[formName].validate(valid => {
        if (valid) {
          axios.post('/register', this.ruleForm).then(response => { // 用户名和密码将转为json传到后台接口
            let res = response.data // 用res承接返回后台的json文件(像使用数组那样)
            if (res.flag == true) { // 显示登录结果
              console.log('注册成功')
              this.$message({
                showClose: true,
                message: '注册成功！',
                type: 'success',
                center: true
              })

              this.$router.push({
                path: '/login'
              })
            } else if (res.flag == false) { // 显示登录结果
              console.log('账户名已被使用！')
              this.$message({
                showClose: true,
                message: '账户名已被使用！',
                type: 'error',
                center: true
              })
            } else {
              console.log('注册失败')
              this.$message({
                showClose: true,
                message: '注册失败！请稍后重试！',
                type: 'error',
                center: true
              })
            }
          })
        } else {
          console.log('抱歉！注册失败！请稍后重试！')
          return false
        }
      })
    },
    toLog () {
      this.$router.push({
        path: '/login'
      })
    }
  }
}
</script>

<style>
    .logo {
        /* margin-top: 20%; */
        margin: 0 auto;
    }

    .regImg {
        width: 700px;
        margin: 0 auto;
        float: right;
    }

    /* ------------------------------ */
    .title {
        margin-top: 5%;
        width: 100%;
        text-align: center;
    }

    .head {
        width: 350px;
    }

    .username {
        width: 40%;
        margin-top: 20px;
        margin-left: auto;
        margin-right: auto;
    }

    .password {
        width: 40%;
        margin-top: 20px;
        margin-left: auto;
        margin-right: auto;
    }

    .id {
        width: 40%;
        margin-top: 30px;
        margin-left: auto;
        margin-right: auto;
    }

    .login {
        margin-top: 8%;
        margin-left: auto;
        margin-right: auto;
        text-align: center;
    }

    .logBtn {
        width: 150px;
        height: 40px;
        border-radius: 25px;
        border: none;
        outline: none;
        background-color: #4F6E9D;
        color: #FFFFFF;
        box-shadow: 0 2px 4px rgba(0, 0, 0, .12), 0 0 6px rgba(0, 0, 0, .04);
    }

    .link {
        margin-top: 20px;
        margin-bottom: 50px;
        margin-left: auto;
        margin-right: auto;
        text-align: center;
    }
</style>
