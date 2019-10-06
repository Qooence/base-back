<template>
    <div>
      <Card class="card-search">
        <Form :model="searchParam" :label-width="80">
          <Row>
            <Col span="21">
              <FormItem label="设备名称">
                <Input v-model="searchParam.name" style="width:200px" placeholder="名称" clearable></Input>
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
          type: 'selection',
          width: 60,
          align: 'center'
        },
        {
          title: '设备型号',
          key: 'devType',
          tooltip: true,
          align: 'center'
        },
        {
          title: '设备名',
          key: 'name',
          tooltip: true,
          align: 'center'
        },
        {
          title: '版本号',
          key: 'version',
          tooltip: true,
          align: 'center'
        },
        {
          title: '本机IP',
          key: 'hostId',
          tooltip: true,
          align: 'center'
        },
        {
          title: '服务器IP',
          key: 'serverIp',
          tooltip: true,
          align: 'center'
        },
        {
          title: '服务器端口',
          key: 'serverPort',
          tooltip: true,
          align: 'center'
        },
        {
          title: '状态',
          align: 'center',
          render: (h, params) => {
            if (!params.row.state) return
            return h(
              'Tag',
              {
                props: {
                  color: params.row.state * 1 !== 4 ? 'success' : 'error'
                }
              }, this.stateName[params.row.state * 1 - 1]
            )
          }
        },
        {
          title: '内置发码器',
          align: 'center',
          render: (h, params) => {
            if (!params.row.switchState) return
            return h(
              'Tag',
              {
                props: {
                  color: params.row.switchState * 1 ? 'success' : 'error'
                }
              }, params.row.switchState * 1 ? '打开' : '关闭'
            )
          }
        },
        {
          title: '是否在线',
          align: 'center',
          render: (h, params) => {
            if (!params.row.isOnline) return
            return h(
              'Tag',
              {
                props: {
                  color: params.row.isOnline * 1 ? 'success' : 'error'
                }
              }, params.row.isOnline * 1 ? '在线' : '离线'
            )
          }
        },
        {
          title: '创建时间',
          key: 'createTime',
          tooltip: true,
          align: 'center'
        },
        {
          title: '更新时间',
          key: 'updateTime',
          tooltip: true,
          align: 'center'
        }
      ],
      listData: [],
      totalCount: 0,
      stateName: ['布防', '撤防', '在家布防', 'sos报警']
    }
  },
  methods: {
    ...mapActions(['getDeviceList']),
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
      this.getDeviceList(this.searchParam).then(res => {
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
