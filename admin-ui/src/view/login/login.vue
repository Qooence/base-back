<style lang="less">
  @import './login.less';
</style>

<template>
  <div class="login">
    <div class="login-con">
      <Card icon="log-in" title="欢迎登录" :bordered="false">
        <div class="form-con">
          <login-form @on-success-valid="handleSubmit" ref="loginForm"></login-form>
        </div>
      </Card>
    </div>
  </div>
</template>

<script>
import LoginForm from '_c/login-form'
import { mapActions } from 'vuex'
export default {
  components: {
    LoginForm
  },
  methods: {
    ...mapActions([
      'handleLogin',
      'getUserInfo'
    ]),
    handleSubmit ({ username, password, uuid, captcha }) {
      this.handleLogin({ username, password, uuid, captcha }).then(res => {
        this.getUserInfo().then(res => {
          this.$router.push({
            name: this.$config.homeName
          })
        }).catch(err => {
          console.log(err)
        })
      },
      err => {
        this.$Notice.error({
          title: '错误',
          desc: err.msg
        })
        this.$refs.loginForm.getCaptcha()
      })
    }
  }
}
</script>

<style>

</style>
