local kong = kong
local http = require "resty.http"
local cjson = require("cjson.safe")

local CustomAuthHandler = {
  PRIORITY = 1000,
  VERSION = "1.0",
}

function CustomAuthHandler:access(config)
  -- Use the auth_service_url from the configuration
  local auth_service_url = config.auth_service_url
  kong.log.debug("url ",auth_service_url)
  -- Call auth service
  local httpc = http.new()
  httpc:set_timeouts(10000, 10000, 10000)
  local res, err = httpc:request_uri(auth_service_url, {
    method = "GET",
    headers = {
      ["Authorization"] = kong.request.get_header("Authorization")
    }
  })

  if not res then
    kong.log.err("Failed to call auth service: ", err)
    return kong.response.exit(500, { message = "Internal Server Error" })
  end
   if res.status ~= 200 then
    kong.log.debug("res",res.status)
    return kong.response.exit(res.status, { message = "Unauthorized" })
  end
  kong.log.debug("userId" , res.body)
  local user_id = res.body


  -- If authenticated, add user_id to headers and continue
  --local user_id = decoded_body.userId -- Assuming auth service returns user_id in body
  kong.service.request.set_header("X-User-ID", user_id)
end

return CustomAuthHandler