<template>
    <div>
      <Card class="card-search">
        <Form :model="searchParam" :label-width="80">
          <Row>
            <Col span="11">
              <FormItem label="关键字">
                <Input v-model="searchParam.key" style="width:200px" placeholder="用户名/用户操作" clearable></Input>
              </FormItem>
            </Col>
            <Col span="10">
              <FormItem label="操作时间">
                <DatePicker type="daterange"
                  split-panels
                  placement="bottom-start"
                  placeholder="时间范围"
                  style="width: 200px"
                  @on-change="timeConfir">
                </DatePicker>
              </FormItem>
            </Col>
            <Col span="3" class=" card-search-search-btn-box" :label-width="10">
              <Button type="primary" @click="search">搜索</Button>
            </Col>
          </Row>
        </Form>
      </Card>
      <Card class="card-content">
        <div class="card-content-table">
          <Table border
            ref="selection"
            :loading="loading"
            :columns="colData"
            :data="listData">
          </Table>
        </div>
        <div class="card-content-pages">
          <Page :total="totalCount"
            show-elevator
            show-total
            :page-size="searchParam.limit"
            @on-change='change'
            @on-page-size-change='change'>
          </Page>
        </div>
      </Card>
    </div>
</template>
<script>
import { mapActions } from 'vuex'
export default {
  data () {
    return {
      searchParam: {
        page: 1,
        limit: 10
      },
      loading: true,
      colData: [
        {
          title: '用户名',
          key: 'username',
          tooltip: true,
          align: 'center'
        },
        {
          title: '用户操作',
          key: 'operation',
          tooltip: true,
          align: 'center'
        },
        {
          title: '请求方法',
          key: 'method',
          tooltip: true,
          align: 'center'
        },
        {
          title: '请求参数',
          key: 'params',
          tooltip: true,
          align: 'center'
        },
        {
          title: '执行时长(毫秒)',
          key: 'time',
          tooltip: true,
          align: 'center'
        },
        {
          title: 'IP地址',
          key: 'ip',
          tooltip: true,
          align: 'center'
        },
        {
          title: '创建时间',
          key: 'createDate',
          tooltip: true,
          align: 'center'
        }
      ],
      listData: [],
      totalCount: 0
    }
  },
  methods: {
    ...mapActions(['getLogList']),
    search () {
      this.searchParam.page = 1
      this.initData()
    },
    change (value) {
      this.searchParam.page = value
      this.initData()
    },
    initData () {
      this.loading = true
      this.getLogList(this.searchParam).then(res => {
        this.loading = false
        this.listData = res.data.page.list
        this.totalCount = res.data.page.totalCount
      }).catch(err => {
        this.loading = false
        this.$Notice.error({
          title: '错误',
          desc: err.msg
        })
      })
    },
    timeConfir (res) {
      this.searchParam.createDate = res.filter(item => item !== null && item !== '')
    }
  },
  mounted () {
    this.initData()
  }
}
</script>
<style lang="less">
  @import "~@/style/style.less";
</style>
