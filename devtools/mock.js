const path = require("path")
const Koa = require("koa")
const Router = require("koa-router")
const glob = require("glob")
const cors = require('@koa/cors')

const app = new Koa()
const router = new Router()

function sleep(time) {
  return new Promise(function(resolve, reject) {
    setTimeout(function() {
      resolve()
    }, time)
  })
}

glob("./src/**/__apimocks__/*.js", async (err, files) => {
  if (err) throw err
  files.forEach(p => {
    const mock = require(path.resolve(process.cwd(), p))
    router[mock.method || "all"](mock.path, async (ctx, next) => {
      ctx.body = mock.response

      if (mock.delay) {
        await sleep(mock.delay)
      }
    })
  })
})

app.use(cors({ credentials: true }))
app.use(router.routes()).use(router.allowedMethods())

app.listen(3000)
