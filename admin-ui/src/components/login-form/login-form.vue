<template>
  <Form ref="loginForm" :model="form" :rules="rules" @keydown.enter.native="handleSubmit">
    <FormItem prop="username">
      <Input v-model="form.username" placeholder="请输入用户名">
        <span slot="prepend">
          <Icon :size="16" type="ios-person"></Icon>
        </span>
      </Input>
    </FormItem>
    <FormItem prop="password">
      <Input type="password" v-model="form.password" placeholder="请输入密码">
        <span slot="prepend">
          <Icon :size="16" type="md-lock"></Icon>
        </span>
      </Input>
    </FormItem>
    <Col span="14">
      <FormItem prop="captcha">
          <Input v-model="form.captcha" placeholder="请输入验证码">
              <span slot="prepend">
                  <Icon :size="16" type="md-closed-captioning"></Icon>
              </span>
          </Input>
      </FormItem>
    </Col>
    <img  @click="getCaptcha" :src="captchaPath" style="width:110px;height:32px;cursor: pointer;">
    <FormItem>
      <Button @click="handleSubmit" type="primary" long>登录</Button>
    </FormItem>
  </Form>
</template>
<script>
import { getUUID } from '@/libs/tools'
import { mapActions } from 'vuex'
export default {
  name: 'LoginForm',
  props: {
    usernameRules: {
      type: Array,
      default: () => {
        return [
          { required: true, message: '账号不能为空', trigger: 'blur' }
        ]
      }
    },
    passwordRules: {
      type: Array,
      default: () => {
        return [
          { required: true, message: '密码不能为空', trigger: 'blur' }
        ]
      }
    },
    captchaRules: {
      type: Array,
      default: () => {
        return [
          { required: true, message: '验证码不能为空', trigger: 'blur' }
        ]
      }
    }
  },
  created () {
    this.getCaptcha()
  },
  data () {
    return {
      captchaPath: '',
      form: {
        username: '',
        password: '',
        captcha: '',
        uuid: ''
      }
    }
  },
  computed: {
    rules () {
      return {
        username: this.usernameRules,
        password: this.passwordRules,
        captcha: this.captchaRules
      }
    }
  },
  methods: {
    ...mapActions([
      'refreshCaptcha'
    ]),
    handleSubmit () {
      this.$refs.loginForm.validate((valid) => {
        if (valid) {
          this.$emit('on-success-valid', {
            username: this.form.username,
            password: this.form.password,
            uuid: this.form.uuid,
            captcha: this.form.captcha
          })
        }
      })
    },
    // 获取验证码
    getCaptcha () {
      this.form.uuid = getUUID()
      this.refreshCaptcha({ uuid: this.form.uuid }).then(res => {
        this.captchaPath = 'data:image/png;base64,' + btoa(new Uint8Array(res.data).reduce((data, byte) => data + String.fromCharCode(byte), '')
        )
      })
    }
  }
}
</script>
