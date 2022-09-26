using AutoMapper;
using Carer.Model.DomainModels;
using Carer.Service.Common;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Net.Http;
using System.Web;
using System.Web.Http;

namespace CarerServer.WebAPI.Controllers
{
        public class OwnerReviewController : ApiController
        {
            IUserService userService;
            IMapper mapper;

            public OwnerReviewController(IUserService userService, IMapper mapper)
            {
                this.userService = userService;
                this.mapper = mapper;
            }

            public HttpResponseMessage PostOwnerReview(OwnerReviewInputModel review)
            {
                userService.AddOwnerReview(mapper.Map<OwnerReviewInputModel, OwnerReviewDomainModel>(review));
                return Request.CreateResponse(System.Net.HttpStatusCode.OK);
            }
        }
    
}