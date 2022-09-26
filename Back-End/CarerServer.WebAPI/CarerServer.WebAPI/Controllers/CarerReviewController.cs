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
    public class CarerReviewController: ApiController
    {
        IUserService userService;
        IMapper mapper;

        public CarerReviewController(IUserService userService, IMapper mapper)
        {
            this.userService = userService;
            this.mapper = mapper;
        }

        public HttpResponseMessage PostCarerReview(CarerReviewInputModel review)
        {
            userService.AddCarerReview(mapper.Map<CarerReviewInputModel,CarerReviewDomainModel>(review));
            return Request.CreateResponse(System.Net.HttpStatusCode.OK);
        }
    }
}